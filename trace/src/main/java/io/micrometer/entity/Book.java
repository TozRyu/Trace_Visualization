package io.micrometer.entity;

import lombok.Data;

@Data
public class Book {

    private String title;

    public static Book withTitle(String title) {
        Book book = new Book();
        book.title = title;
        return book;
    }
}
