package io.micrometer.service;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MicrometerCountService {

	/**
	 * 方式 1：像使用 slf4j 一样，使用 `io.micrometer.core.instrument.Metrics`静态方式初始化一个计数器，适用于名字和 Tag
	 * 固定的场景
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
