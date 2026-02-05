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

    // ==========================================================
    // ==================== Helper Methods =======================
    // ==========================================================

    private boolean bookIdExists(Long id) {
        return books.stream().anyMatch(b -> b.getId().equals(id));
    }

    private boolean memberIdExists(Long id) {
        return members.stream().anyMatch(m -> m.getId().equals(id));
    }

    private boolean recordIdExists(Long id) {
        return borrowingRecords.stream().anyMatch(r -> r.getId().equals(id));
    }

    // ==========================================================
    // ==================== Book Methods =========================
    // ==========================================================

    public List<Book> getAllBooks() {
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        if (id == null) return Optional.empty();

        return books.stream()
                .filter(book -> book.getId() != null && book.getId().equals(id))
                .findFirst();
    }

    public void addBook(Book book) {

        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }

        if (book.getId() == null) {
            throw new IllegalArgumentException("Book id is required.");
        }

        if (bookIdExists(book.getId())) {
            throw new IllegalArgumentException("Book id already exists: " + book.getId());
        }

        books.add(book);
    }

    public void updateBook(Book updatedBook) {

        if (updatedBook == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }

        if (updatedBook.getId() == null) {
            throw new IllegalArgumentException("Book id is required for update.");
        }

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);

            if (book.getId() != null && book.getId().equals(updatedBook.getId())) {
                books.set(i, updatedBook);
                return;
            }
        }

        throw new IllegalArgumentException("Book not found with id: " + updatedBook.getId());
    }

    public void deleteBook(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Book id is required for delete.");
        }

        boolean removed = books.removeIf(book -> book.getId() != null && book.getId().equals(id));

        if (!removed) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }
    }

    // ==========================================================
    // ==================== Member Methods =======================
    // ==========================================================

    public List<Member> getAllMembers() {
        return members;
    }

    public Optional<Member> getMemberById(Long id) {
        if (id == null) return Optional.empty();

        return members.stream()
                .filter(member -> member.getId() != null && member.getId().equals(id))
                .findFirst();
    }

    public void addMember(Member member) {

        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }

        if (member.getId() == null) {
            throw new IllegalArgumentException("Member id is required.");
        }

        if (memberIdExists(member.getId())) {
            throw new IllegalArgumentException("Member id already exists: " + member.getId());
        }

        members.add(member);
    }

    public void updateMember(Member updatedMember) {

        if (updatedMember == null) {
            throw new IllegalArgumentException("Member cannot be null.");
        }

        if (updatedMember.getId() == null) {
            throw new IllegalArgumentException("Member id is required for update.");
        }

        for (int i = 0; i < members.size(); i++) {
            Member member = members.get(i);

            if (member.getId() != null && member.getId().equals(updatedMember.getId())) {
                members.set(i, updatedMember);
                return;
            }
        }

        throw new IllegalArgumentException("Member not found with id: " + updatedMember.getId());
    }

    public void deleteMember(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Member id is required for delete.");
        }

        boolean removed = members.removeIf(member -> member.getId() != null && member.getId().equals(id));

        if (!removed) {
            throw new IllegalArgumentException("Member not found with id: " + id);
        }
    }

    // ==========================================================
    // ================= BorrowingRecord Methods =================
    // ==========================================================

    public List<BorrowingRecord> getAllBorrowingRecords() {
        return borrowingRecords;
    }

    public void borrowBook(BorrowingRecord record) {

        if (record == null) {
            throw new IllegalArgumentException("BorrowingRecord cannot be null.");
        }

        if (record.getId() == null) {
            throw new IllegalArgumentException("BorrowingRecord id is required.");
        }

        if (recordIdExists(record.getId())) {
            throw new IllegalArgumentException("BorrowingRecord id already exists: " + record.getId());
        }

        if (record.getBook() == null || record.getBook().getId() == null) {
            throw new IllegalArgumentException("Book (with id) is required.");
        }

        if (record.getMember() == null || record.getMember().getId() == null) {
            throw new IllegalArgumentException("Member (with id) is required.");
        }

        // ตรวจสอบว่ามี book/member จริงในระบบ
        Book book = getBookById(record.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + record.getBook().getId()));

        Member member = getMemberById(record.getMember().getId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found with id: " + record.getMember().getId()));

        // ตรวจสอบจำนวนหนังสือ
        if (book.getAvailableCopies() <= 0) {
            throw new IllegalArgumentException("Book is out of stock. Cannot borrow.");
        }

        // เซ็ตค่าตามระบบ
        record.setBook(book);
        record.setMember(member);

        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setReturnDate(null);

        borrowingRecords.add(record);

        // ลดจำนวนหนังสือ
        book.setAvailableCopies(book.getAvailableCopies() - 1);
    }

    public void returnBook(Long recordId, LocalDate returnDate) {

        if (recordId == null) {
            throw new IllegalArgumentException("Record id is required.");
        }

        if (returnDate == null) {
            throw new IllegalArgumentException("Return date is required.");
        }

        for (BorrowingRecord record : borrowingRecords) {

            if (record.getId() != null && record.getId().equals(recordId)) {

                if (record.getReturnDate() != null) {
                    throw new IllegalArgumentException("This record has already been returned.");
                }

                record.setReturnDate(returnDate);

                Book book = record.getBook();
                if (book != null) {
                    book.setAvailableCopies(book.getAvailableCopies() + 1);
                }

                return;
            }
        }

        throw new IllegalArgumentException("BorrowingRecord not found with id: " + recordId);
    }
}
