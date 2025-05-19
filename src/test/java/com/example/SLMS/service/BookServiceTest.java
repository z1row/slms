package com.example.SLMS.service;

import com.example.SLMS.dto.request.CreateBookRequest;
import com.example.SLMS.dto.response.BookCopyResponse;
import com.example.SLMS.dto.response.BookResponse;
import com.example.SLMS.dto.response.BookWithCopiesResponse;
import com.example.SLMS.entity.Book;
import com.example.SLMS.entity.BookCopy;
import com.example.SLMS.exception.BookNotFoundException;
import com.example.SLMS.exception.DuplicateBookTitleException;
import com.example.SLMS.mapper.BookMapper;
import com.example.SLMS.repository.BookRepository;
import com.example.SLMS.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testCreateBook_Success() {
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Test Book");
        request.setAuthor("John Doe");
        request.setIsbn("1234567891011");
        request.setPublishedYear(2022);

        Book bookEntity = new Book();
        bookEntity.setTitle(request.getTitle());
        bookEntity.setAuthor(request.getAuthor());
        bookEntity.setIsbn(request.getIsbn());
        bookEntity.setPublishedYear(request.getPublishedYear());

        Book savedBook = new Book();
        savedBook.setId(1L);
        savedBook.setTitle(request.getTitle());
        savedBook.setAuthor(request.getAuthor());
        savedBook.setIsbn(request.getIsbn());
        savedBook.setPublishedYear(request.getPublishedYear());

        BookResponse response = new BookResponse();
        response.setId(1L);
        response.setTitle(request.getTitle());
        response.setAuthor(request.getAuthor());
        response.setIsbn(request.getIsbn());
        response.setPublishedYear(request.getPublishedYear());

        when(bookMapper.toEntity(request)).thenReturn(bookEntity);
        when(bookRepository.existsByTitle(bookEntity.getTitle())).thenReturn(false);
        when(bookRepository.existsByIsbn(bookEntity.getIsbn())).thenReturn(false);
        when(bookRepository.save(bookEntity)).thenReturn(savedBook);
        when(bookMapper.toResponse(savedBook)).thenReturn(response);

        BookResponse result = bookService.createBook(request);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Book", result.getTitle());

        verify(bookMapper).toEntity(request);
        verify(bookRepository).existsByTitle(bookEntity.getTitle());
        verify(bookRepository).existsByIsbn(bookEntity.getIsbn());
        verify(bookRepository).save(bookEntity);
        verify(bookMapper).toResponse(savedBook);
    }

    @Test
    public void testCreateBook_DuplicateTitle() {
        CreateBookRequest request = new CreateBookRequest();
        request.setTitle("Duplicate Title");
        request.setAuthor("John Doe");
        request.setIsbn("1234567891011");
        request.setPublishedYear(2022);

        Book bookEntity = new Book();
        bookEntity.setTitle(request.getTitle());
        bookEntity.setAuthor(request.getAuthor());
        bookEntity.setIsbn(request.getIsbn());
        bookEntity.setPublishedYear(request.getPublishedYear());

        when(bookMapper.toEntity(request)).thenReturn(bookEntity);
        when(bookRepository.existsByTitle(bookEntity.getTitle())).thenReturn(true);

        assertThrows(DuplicateBookTitleException.class, () -> {
            bookService.createBook(request);
        });

        verify(bookMapper).toEntity(request);
        verify(bookRepository).existsByTitle(bookEntity.getTitle());
        verify(bookRepository, never()).existsByIsbn(any());
        verify(bookRepository, never()).save(any());
    }

    @Test
    public void testGetBookWithCopiesById_Success() {
        Long bookId = 1L;
        String bookTitle = "Test Book";
        String bookAuthor = "Test Author";
        String bookIsbn = "1234567891011";
        Integer bookPublishYear = 2022;

        BookCopy bookCopy = new BookCopy();
        bookCopy.setId(1L);
        bookCopy.setAvailable(true);

        BookCopyResponse bookCopyResponse = new BookCopyResponse();
        bookCopy.setId(1L);
        bookCopy.setAvailable(true);

        Book book = new Book();
        book.setId(bookId);
        book.setTitle(bookTitle);
        book.setAuthor(bookAuthor);
        book.setIsbn(bookIsbn);
        book.setPublishedYear(bookPublishYear);
        book.setCopies(List.of(bookCopy));

        BookWithCopiesResponse response = new BookWithCopiesResponse();
        response.setId(bookId);
        response.setTitle(bookTitle);
        response.setAuthor(bookAuthor);
        response.setIsbn(bookIsbn);
        response.setPublishedYear(bookPublishYear);
        response.setCopies(List.of(bookCopyResponse));

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toResponseWithCopies(book)).thenReturn(response);

        BookWithCopiesResponse result = bookService.getBookWithCopiesById(bookId);

        assertNotNull(result);
        assertEquals(bookId, result.getId());
        assertEquals(bookTitle, result.getTitle());
        assertEquals(bookAuthor, result.getAuthor());
        assertEquals(bookIsbn, result.getIsbn());
        assertEquals(bookPublishYear, result.getPublishedYear());
        assertEquals(List.of(bookCopyResponse), result.getCopies());

        verify(bookRepository).findById(bookId);
        verify(bookMapper).toResponseWithCopies(book);
    }

    @Test
    public void testGetBookWithCopiesById_NotFound() {
        Long bookId = 2L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> {
            bookService.getBookWithCopiesById(bookId);
        });

        verify(bookRepository).findById(bookId);
        verifyNoInteractions(bookMapper);
    }
}

