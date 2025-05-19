package com.example.SLMS.exception;

public class DuplicateBookIsbnException extends DuplicateBookException{
    public DuplicateBookIsbnException(String isbn) {
        super("ISBN", isbn);
    }
}
