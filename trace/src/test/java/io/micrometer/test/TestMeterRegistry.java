package io.micrometer.test;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestMeterRegistry {

    public static void main(String[] args) {
        // 创建一个 MeterRegistry 实例
        MeterRegistry registry = new SimpleMeterRegistry();

        // 创建和注册各种度量
        Counter counter = registry.counter("my_counter");
        // 定时器可以用于测量方法执行时间
        Timer timer = registry.timer("my_timer");
        AtomicInteger myGaugeValue = new AtomicInteger(0);
        // 测量值（Gauge）适用于监控瞬时数据
        Gauge gauge = Gauge.builder("my_gauge", myGaugeValue, AtomicInteger::get).register(registry);
        DistributionSummary summary = registry.summary("my_summary");

        // 使用度量 计数器可以用于跟踪事件的发生次数，例如，HTTP 请求的数量
        counter.increment();
        timer.record(() -> {
            // 模拟一些操作
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        myGaugeValue.set(42);

        // 分布摘要可以用于收集数值的分布情况
        summary.record(5.0);

        // 获取度量值
        System.out.println("Counter 值: " + counter.count());
        System.out.println("Timer 总时间: " + timer.totalTime(TimeUnit.MILLISECONDS) + " 毫秒");
        System.out.println("Gauge 值: " + gauge.value());
        System.out.println("Summary 总数: " + summary.count());
    }
}
