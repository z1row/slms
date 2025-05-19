package com.example.SLMS.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title must not be blank")
    @Column(nullable = false, unique = true)
    private String title;

    @NotBlank(message = "Author must not be blank")
    @Column(nullable = false)
    private String author;

    @NotBlank(message = "ISBN must not be blank")
    @Pattern(regexp = "\\d{13}", message = "ISBN must be a 13-digit number")
    @Column(nullable = false, unique = true)
    private String isbn;

    @NotNull(message = "Published year is required")
    @Min(value = 1, message = "Published year must be a valid 4-digit year")
    @Max(value = 9999, message = "Published year must be a valid 4-digit year")
    @Column(nullable = false)
    private Integer publishedYear;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookCopy> copies;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<BookCopy> getCopies() {
        return copies;
    }

    public void setCopies(List<BookCopy> copies) {
        this.copies = copies;
    }
}
