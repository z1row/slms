package com.example.SLMS.repository;

import com.example.SLMS.entity.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findAllByBookIdAndAvailable(Long bookId, Boolean available);
}
