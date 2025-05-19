package com.example.SLMS.exception;

import jakarta.persistence.EntityNotFoundException;

public class BookCopyNotFoundException extends EntityNotFoundException {
    public BookCopyNotFoundException(Long id) {
        super("BookCopy with ID '" + id + "' not found.");
    }
}
