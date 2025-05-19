package com.example.SLMS.dto.request;

import jakarta.validation.constraints.*;

public class CreateBookRequest {
        @NotBlank(message = "Title must not be blank")
        private String title;

        @NotBlank(message = "Author must not be blank")
        private String author;

        @NotBlank(message = "ISBN must not be blank")
        @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number")
        private String isbn;

        @NotNull(message = "Published year is required")
        @Min(value = 1, message = "Published year must be a valid 4-digit year")
        @Max(value = 9999, message = "Published year must be a valid 4-digit year")
        private Integer publishedYear;

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getAuthor() {
                return author;
        }

        public void setAuthor(String author) {
                this.author = author;
        }

        public String getIsbn() {
                return isbn;
        }

        public void setIsbn(String isbn) {
                this.isbn = isbn;
        }

        public Integer getPublishedYear() {
                return publishedYear;
        }

        public void setPublishedYear(Integer publishedYear) {
                this.publishedYear = publishedYear;
        }
}
