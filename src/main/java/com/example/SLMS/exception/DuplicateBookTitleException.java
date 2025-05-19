package com.example.SLMS.exception;

public class DuplicateBookTitleException extends DuplicateBookException {
    public DuplicateBookTitleException(String title) {
      super("title", title);
    }
}
