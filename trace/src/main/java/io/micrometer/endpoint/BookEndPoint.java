package io.micrometer.endpoint;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.entity.Book;
import io.micrometer.service.MicroObService;
import io.micrometer.service.MicrometerCountService;
import io.micrometer.service.MicrometerTimedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/trace/books")
public class BookEndPoint {

	private final MeterRegistry meterRegistry;

	@Autowired
	MicrometerCountService countService;

	@Autowired
	MicrometerTimedService timedService;

	@Autowired
	MicroObService obService;

	public BookEndPoint(MeterRegistry registry) {
		this.meterRegistry = registry;
	}

	// @Timed(value="greeting",description = "time taken to return greeting")
	@GetMapping("/getBooks")
	public ResponseEntity<List<Book>> getBooks(@RequestParam("title") String title) {
		// Counter counter = Counter.builder("trace/books/get")
		// .tag("title", title)
		// .description("a number of requests to /trace/books endpoint")
		// .register(meterRegistry);
		//
		// Counter prepare = meterRegistry.counter("http.request.total", "endpoint",
		// "trace/books/get");
		//
		// counter.increment();
		// prepare.increment();

		List<Book> books = new ArrayList<>();
		books.add(Book.withTitle(title));
		countService.sendByAli();
		countService.sendByHuawei();
		timedService.sendByAli();
		timedService.sendByHuawei();
		obService.call();
		return ResponseEntity.ok(books);
	}

}
