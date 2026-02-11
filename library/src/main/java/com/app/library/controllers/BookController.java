package com.app.library.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.library.model.Book;
import com.app.library.service.LibraryService;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // GET all books
    @GetMapping
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    // POST add book
    @PostMapping
    public Book addBook(@RequestBody Book book) {
        libraryService.addBook(book);
        return book;
    }

    // PUT update book
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id); // ✅ ใช้ id จาก path เสมอ
        libraryService.updateBook(book);
        return book;
    }

    // DELETE book
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        libraryService.deleteBook(id);
    }
}
