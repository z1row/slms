package com.example.SLMS.exception;

public class DuplicateBookException extends IllegalArgumentException {
  public DuplicateBookException(String paramName, String paramValue) {
    super("Book with " + paramName + " '" + paramValue + "' already exists");
  }
}
