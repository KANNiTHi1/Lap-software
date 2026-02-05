package com.app.library.controllers;

import com.app.library.model.Book;
import com.app.library.service.LibraryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        libraryService.addBook(book);
        return ResponseEntity.ok("Book added successfully");
    }
}
