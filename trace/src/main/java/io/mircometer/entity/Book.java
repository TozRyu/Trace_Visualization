package io.mircometer.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class Book {

    private String title;

    public static Book withTitle(String title) {
        Book book = new Book();
        book.title = title;
        return book;
    }
}
