package io.mircometer.endpoint;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.mircometer.entity.Book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/trace/books")
public class BookEndPoint {

    private final MeterRegistry meterRegistry;

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam String title) {

        Counter counter = Counter.builder("trace/books/get")
                .tag("title", title)
                .description("a number of requests to /trace/books endpoint")
                .register(meterRegistry);
        counter.increment();

        List<Book> books = new ArrayList<>();
        books.add(Book.withTitle(title));
        return ResponseEntity.ok(books);
    }

}
