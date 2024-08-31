package io.micrometer.entity;

import io.micrometer.core.annotation.Timed;
import lombok.Data;

@Data
public class Book {

	private String title;

	/**
	 * 使用 @Timed @Counted 注解。注意注解方式必须等方法调用后才能生成指标。 而静态方法形式
	 * io.micrometer.core.instrument.Metrics.counter立即就生成指标只是值为 0。 另外注意 Spring 注解不支持
	 * private、default 级别方法。 MeterRegistry 已经做了缓存，参考
	 * {@link io.micrometer.core.instrument.MeterRegistry}#registerMeterIfNecessary
	 * @param title 标题
	 * @return {@link Book}
	 */

	public static Book withTitle(String title) {
		Book book = new Book();
		book.title = title;
		return book;
	}

}
