package com.example.SLMS.service;

import com.example.SLMS.dto.request.CreateBookRequest;
import com.example.SLMS.dto.request.UpdateBookRequest;
import com.example.SLMS.dto.response.BookCopyResponse;
import com.example.SLMS.dto.response.BookResponse;
import com.example.SLMS.dto.response.BookWithCopiesResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
    BookResponse createBook(@Valid CreateBookRequest book);
    BookWithCopiesResponse getBookWithCopiesById(Long id);
    Page<BookResponse> getAllBooks(Pageable pageable);
    BookResponse updateBook(Long id, @Valid UpdateBookRequest updates);
    void deleteBook(Long id);

    List<BookCopyResponse> getAvailableCopiesOfABook(Long id);
    BookCopyResponse addBookCopy(Long id);
    BookCopyResponse updateCopyAvailability(Long bookId, Long copyId, boolean available);
}
