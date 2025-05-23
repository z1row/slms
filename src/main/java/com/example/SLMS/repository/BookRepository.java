package com.example.SLMS.repository;

import com.example.SLMS.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    boolean existsByTitle(String title);
    Optional<Book> findByIsbn(String isbn);
}
