package io.micrometer.test;

import brave.Tracing;
import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagation;
import brave.baggage.BaggagePropagationConfig;
import brave.handler.SpanHandler;
import brave.propagation.B3Propagation;
import brave.propagation.StrictCurrentTraceContext;
import brave.sampler.Sampler;
import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.brave.bridge.BraveBaggageManager;
import io.micrometer.tracing.brave.bridge.BraveCurrentTraceContext;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.brave.ZipkinSpanHandler;
import zipkin2.reporter.okhttp3.OkHttpSender;

public class MicrometerTracingBrave {

	/**
	 * <a href="https://segmentfault.com/a/1190000039871489?sort=newest">链路追踪多方式</a>
	 * @param args
	 */
	public static void main(String[] args) {

		// [Brave component]使用SpanHandler的示例。
		// SpanHandler是一个在完成跨度时调用的组件。
		// 这里我们有一个设置示例，通过UrlConnectionSender（通过<io.Zipkin.reporter2:Zipkin-sender-urlconnection>依赖项）
		// 以Zipkin格式将跨度发送到所提供的位置。另一个选项可以是使用TestSpanHandler进行测试

		// 记录Span的方式。默认以日志的方式记录（LoggingReporter）
		AsyncReporter<zipkin2.Span> reporter = AsyncReporter
			.create(OkHttpSender.create("http://10.188.188.170:9411/api/v2/spans"));

		SpanHandler spanHandler = ZipkinSpanHandler.create(reporter);
		// 〔Brave组件〕CurrentTraceContext是一个Brave组件，它允许您检索当前TraceContext。
		StrictCurrentTraceContext braveCurrentTraceContext = StrictCurrentTraceContext.create();
		// [Micrometer Tracing 组件] Brave的CurrentTraceContext的Micrometer包装类型
		// 当前跟踪上下文. 默认使用一个静态的InheritableThreadLocal自动继承这个context
		CurrentTraceContext bridgeContext = new BraveCurrentTraceContext(braveCurrentTraceContext);
		// [Brave组件]跟踪是根组件，允许配置跟踪程序、处理程序、上下文传播等。
		Tracing tracing = Tracing.newBuilder()
			.currentTraceContext(braveCurrentTraceContext)
			.supportsJoin(false)
			// 对Span内容进行序列化和反序列化
			.traceId128Bit(true)
			.propagationFactory(BaggagePropagation.newFactoryBuilder(B3Propagation.FACTORY)
				// 要使Baggage（行李）正常工作，您需要提供要传播的字段列表
				.add(BaggagePropagationConfig.SingleBaggageField.remote(BaggageField.create("from_span_in_scope 1")))
				.build())
			.sampler(Sampler.ALWAYS_SAMPLE)
			.addSpanHandler(spanHandler)
			.build();
		// [Brave 组件] Tracer是用于处理跨度生命周期的组件
		brave.Tracer braveTracer = tracing.tracer();
		// --------上面主要是brave创建Tracer追踪器的逻辑，可以随时替换为其他链路追踪系统-------//
		// --------下面是Micrometer Tracing门面做的操作---------------------------------//
		// [Micrometer Tracing 组件] Micrometer Tracing包装类型，包装了Brave‘s Tracer的Tracer

		Tracer tracer = new BraveTracer(braveTracer, bridgeContext, new BraveBaggageManager());
		// 创建跨度。如果此线程中存在span，它将成为“newSpan”的父级。
		Span newSpan = tracer.nextSpan().name("calculateTax");
		// 开始一个跨度并将其放在范围内。放入范围意味着将范围放在本地线程中，如果已配置，则调整MDC以包含跟踪信息
		try (io.micrometer.tracing.Tracer.SpanInScope ws = tracer.withSpan(newSpan.start())) {
			// 您可以标记一个span-在其上放置一个键值对，以便更好地调试
			newSpan.tag("taxValue", "value");
			// 记录一个事件
			newSpan.event("taxCalculated");
		}
		finally {
			// 一旦完成，记得结束跨度。这将允许收集跨度并将其发送到分布式跟踪系统，例如Zipkin
			newSpan.end();
		}

	}

}
