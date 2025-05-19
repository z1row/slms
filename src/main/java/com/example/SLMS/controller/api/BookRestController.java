package com.example.SLMS.controller.api;

import com.example.SLMS.dto.request.CreateBookRequest;
import com.example.SLMS.dto.request.UpdateBookCopyAvailabilityRequest;
import com.example.SLMS.dto.request.UpdateBookRequest;
import com.example.SLMS.dto.response.BookCopyResponse;
import com.example.SLMS.dto.response.BookResponse;
import com.example.SLMS.dto.response.BookWithCopiesResponse;
import com.example.SLMS.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/books", produces = "application/json")
public class BookRestController {
    private final BookService bookService;

    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get books page")
    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        Sort sort = direction.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BookResponse> response = bookService.getAllBooks(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Create book")
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@RequestBody @Valid CreateBookRequest createBookRequest) {
        BookResponse createdBookResponse = bookService.createBook(createBookRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBookResponse);
    }

    @Operation(summary = "Get book with it's copies")
    @GetMapping("/{id}")
    public ResponseEntity<BookWithCopiesResponse> getBookById(@PathVariable Long id) {
        BookWithCopiesResponse bookWithCopiesResponse = bookService.getBookWithCopiesById(id);
        return ResponseEntity.ok(bookWithCopiesResponse);
    }

    @Operation(summary = "Update book")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id, @RequestBody @Valid UpdateBookRequest updates) {
        BookResponse updatedBookResponse = bookService.updateBook(id, updates);
        return ResponseEntity.ok(updatedBookResponse);
    }

    @Operation(summary = "Delete book")
    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponse> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get available book copies of a book")
    @GetMapping("/{id}/copies")
    public ResponseEntity<List<BookCopyResponse>> getAvailableCopiesOfABook(@PathVariable Long id) {
        List<BookCopyResponse> bookCopiesResponses = bookService.getAvailableCopiesOfABook(id);
        return ResponseEntity.ok(bookCopiesResponses);
    }

    @Operation(summary = "Add a copy for a book")
    @PostMapping("{id}/copies")
    public ResponseEntity<BookCopyResponse> addBookCopyForBook(@PathVariable Long id) {
        BookCopyResponse createdBookCopyResponse = bookService.addBookCopy(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBookCopyResponse);
    }

    @Operation(summary = "Update the availability status of a book copy")
    @PutMapping("/{id}/copies/{copyId}")
    public ResponseEntity<BookCopyResponse> updateAvailabilityOfBookCopy(@PathVariable Long id, @PathVariable Long copyId,
                                                                     @RequestBody @Valid UpdateBookCopyAvailabilityRequest updateBookCopyAvailabilityRequest) {
        BookCopyResponse updatedBookCopyResponse = bookService.updateCopyAvailability(id, copyId, updateBookCopyAvailabilityRequest.getAvailable());
        return ResponseEntity.ok(updatedBookCopyResponse);
    }
}
