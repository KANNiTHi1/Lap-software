package com.app.library.service;

import com.app.library.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service   // 🔴 ถ้าไม่มีอันนี้ = พัง 100%
public class BookClientService {

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        books.add(new Book("Java", "John", 2020, "Programming", 5));
        return books;
    }
}
