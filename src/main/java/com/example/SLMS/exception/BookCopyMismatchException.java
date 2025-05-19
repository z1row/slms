package com.example.SLMS.exception;

public class BookCopyMismatchException extends IllegalArgumentException {
    public BookCopyMismatchException(Long bookCopyId, Long bookId) {
        super("BookCopy with ID " + bookCopyId + " does not belong to Book with ID " + bookId);
    }
}
