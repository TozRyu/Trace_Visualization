package io.micrometer.service;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tags;
import io.prometheus.metrics.model.registry.MultiCollector;
import io.prometheus.metrics.model.registry.PrometheusRegistry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MicrometerCountService {

	/**
	 * 方式 1：像使用 slf4j 一样，使用 `io.micrometer.core.instrument.Metrics`静态方式初始化一个计数器，适用于名字和 Tag
	 * 固定的场景
	 *
	 * {@link io.micrometer.core.instrument.MeterRegistry#counter(io.micrometer.core.instrument.Meter.Id)}
	 * {@link Counter.Builder#register(MeterRegistry))}
	 *
	 * prometheus 的实现 SpringBoot 对外提供的 endpoint 对应的类是 PrometheusScrapeEndpoint，
	 * 其数据是从它内部持有的 {@link PrometheusRegistry#register(MultiCollector)} 对象获取，
	 * register负责维护当前系统中所有的Collector实例。HTTPServer在接收到HTTP请求之后，会从 register 中拿到所有的Collector实例，并调用其collect()方法获取所有样本，
	 * 最后格式化为 Prometheus 的标准输出。register 是 prometheus 客户端提供的能力。
	 * 同样持有 register 还有 PrometheusMeterRegistry，前面提到在 micrometer 中所有的指标数据是存在 meterMap 中的，
	 * 但是在创建 Meter 时，PrometheusMeterRegistry 也会将 Meter 通过 applyToCollector 方法同步到 register，
	 * 这样就实现了 prometheus 和 micrometer 对于指标数据的链接。
	 *
	 */
	private static final Counter failure = Metrics.counter("fs.sms.counted", "result", "failure");

	@Autowired
	private MeterRegistry registry;

	private void sendSms() {
		try {
			// do something
			log.info("sendSms to someone");
		}
		catch (Exception e) {
			failure.increment();
		}
	}

	/**
	 * 方式 2：使用注解的方式，注意需要引入 spring-boot-starter-aop 依赖 Spring Boot annotations like @Timed
	 * / @Transactional need the so-called proxying which happens only between invocations
	 * of public methods.
	 */
	@Counted(value = "fs.sms.counted", extraTags = { "provider", "huawei" })
	public void sendByHuawei() {
		this.sendSms();
	}

	@Counted(value = "fs.sms.counted", extraTags = { "provider", "ali" })
	public void sendByAli() {
		this.sendSms();
	}

	/**
	 * 方式 3：使用 MeterRegistry，适合一些动态 Tag 等高级定制场景
	 * @param result result
	 */
	public void countByResult(String result) {

		registry.counter("fs.sms.counted", "result", result).increment();

		// or
		Counter.builder("fs.sms.counted").description("send sms").tags("result", result).register(registry).increment();
	}

}
