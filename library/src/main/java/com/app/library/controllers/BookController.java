package com.app.library.controller;

import com.app.library.model.Book;
import com.app.library.service.LibraryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        book.setId(id);
        libraryService.updateBook(book);
        return book;
    }

    // ✅ DELETE book
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        libraryService.deleteBook(id);
    }
}
