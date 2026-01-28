package com.app.library.controller;

import com.app.library.model.BorrowingRecord;
import com.app.library.service.LibraryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class BorrowingController {

    private final LibraryService libraryService;

    public BorrowingController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // POST /api/borrow
    @PostMapping("/borrow")
    public BorrowingRecord borrowBook(@RequestBody BorrowingRecord record) {
        libraryService.borrowBook(record);
        return record;
    }
}
