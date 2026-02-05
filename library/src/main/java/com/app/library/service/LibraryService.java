package com.app.library.service;

import com.app.library.model.Book;
import com.app.library.model.Member;
import com.app.library.model.BorrowingRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class LibraryService {

    // ==================== In-memory storage ====================
    private List<Book> books = new ArrayList<>();
    private List<Member> members = new ArrayList<>();
    private List<BorrowingRecord> borrowingRecords = new ArrayList<>();

    // ==================== Helper Methods ====================

    private void validateIdNotNull(Long id, String type) {
        if (id == null) {
            throw new IllegalArgumentException(type + " id is required (cannot be null).");
        }
    }

    private void validateBookIdUnique(Long id) {
        boolean exists = books.stream().anyMatch(b -> b.getId().equals(id));
        if (exists) {
            throw new IllegalArgumentException("Book id " + id + " already exists.");
        }
    }

    private void validateMemberIdUnique(Long id) {
        boolean exists = members.stream().anyMatch(m -> m.getId().equals(id));
        if (exists) {
            throw new IllegalArgumentException("Member id " + id + " already exists.");
        }
    }

    private void validateRecordIdUnique(Long id) {
        boolean exists = borrowingRecords.stream().anyMatch(r -> r.getId().equals(id));
        if (exists) {
            throw new IllegalArgumentException("BorrowingRecord id " + id + " already exists.");
        }
    }

    // ==================== Book Methods ====================

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    public void addBook(Book book) {
        validateIdNotNull(book.getId(), "Book");
        validateBookIdUnique(book.getId());
        books.add(book);
    }

    public void updateBook(Book updatedBook) {
        validateIdNotNull(updatedBook.getId(), "Book");

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getId().equals(updatedBook.getId())) {
                books.set(i, updatedBook);
                return;
            }
        }

        throw new IllegalArgumentException("Book id " + updatedBook.getId() + " not found.");
    }

    public void deleteBook(Long id) {
        boolean removed = books.removeIf(book -> book.getId().equals(id));
        if (!removed) {
            throw new IllegalArgumentException("Book id " + id + " not found.");
        }
    }

    // ==================== Member Methods ====================

    public List<Member> getAllMembers() {
        return members;
    }

    public Optional<Member> getMemberById(Long id) {
        return members.stream()
                .filter(member -> member.getId().equals(id))
                .findFirst();
    }

    public void addMember(Member member) {
        validateIdNotNull(member.getId(), "Member");
        validateMemberIdUnique(member.getId());
        members.add(member);
    }

    public void updateMember(Member updatedMember) {
        validateIdNotNull(updatedMember.getId(), "Member");

        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getId().equals(updatedMember.getId())) {
                members.set(i, updatedMember);
                return;
            }
        }

        throw new IllegalArgumentException("Member id " + updatedMember.getId() + " not found.");
    }

    public void deleteMember(Long id) {
        boolean removed = members.removeIf(member -> member.getId().equals(id));
        if (!removed) {
            throw new IllegalArgumentException("Member id " + id + " not found.");
        }
    }

    // ==================== BorrowingRecord Methods ====================

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecords;
    }

    public void borrowBook(BorrowingRecord record) {

        validateIdNotNull(record.getId(), "BorrowingRecord");
        validateRecordIdUnique(record.getId());

        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        borrowingRecords.add(record);

        Book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() - 1);
    }

    public void returnBook(Long recordId, LocalDate returnDate) {
        for (BorrowingRecord record : borrowingRecords) {
            if (record.getId().equals(recordId)) {
                record.setReturnDate(returnDate);

                Book book = record.getBook();
                book.setAvailableCopies(book.getAvailableCopies() + 1);
                return;
            }
        }

        throw new IllegalArgumentException("BorrowingRecord id " + recordId + " not found.");
    }
}
