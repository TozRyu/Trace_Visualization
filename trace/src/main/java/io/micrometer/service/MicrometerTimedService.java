package io.micrometer.service;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 *  {@link Observed}增加 <a href="http://localhost:10101/actuator/metrics">观察端点</a>
 */
@Log4j2
@Service
public class MicrometerTimedService {

    /**
     * 方式 1：像使用 slf4j 一样，使用 `io.micrometer.core.instrument.Metrics`静态方式初始化一个计数器，适用于名字和 Tag 固定的场景
     */
    private static final Timer timer = Metrics.timer("fs.sms.timed", "ali", "hw");

    @Autowired
    private MeterRegistry registry;

    private void sendSms() {
        try {
            // do something
            log.info("sendSms to someone");
        } catch (Exception e) {
//            timer.count();
            e.printStackTrace();
        }
    }

    /**
     * 方式 2：使用注解的方式，注意需要引入 spring-boot-starter-aop 依赖
     */
    @Timed(value = "fs.sms.timed", description = "method sendByHuawei",extraTags = {"provider", "huawei"})
    public void sendByHuawei() {
        this.sendSms();
    }

    /**
     * percentiles统计方法执行时间的25%，50%，75%和95%分位数
     */
    @Timed(value = "fs.sms.timed", description = "method sendByAli", extraTags = {"provider", "ali"}, percentiles = {0.25, 0.5, 0.75, 0.95})
    public void sendByAli() {
        this.sendSms();
    }

    /**
     * 方式 3：使用 MeterRegistry，适合一些动态 Tag 等高级定制场景
     *
     * @param result result
     */
    public void countByResult(String result) {

        registry.counter("fs.sms.timed", "result", result).increment();

        // or
        Counter.builder("fs.sms.timed")
                .description("send sms")
                .tags("result", result)
                .register(registry)
                .increment();
    }

}
