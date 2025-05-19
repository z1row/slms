package com.example.SLMS.exception;

import jakarta.persistence.EntityNotFoundException;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(Long id) {
        super("Book with ID '" + id + "' not found.");
    }
}
