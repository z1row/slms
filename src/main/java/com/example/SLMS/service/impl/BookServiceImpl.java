package com.example.SLMS.service.impl;

import com.example.SLMS.dto.request.CreateBookRequest;
import com.example.SLMS.dto.request.UpdateBookRequest;
import com.example.SLMS.dto.response.BookCopyResponse;
import com.example.SLMS.dto.response.BookResponse;
import com.example.SLMS.dto.response.BookWithCopiesResponse;
import com.example.SLMS.entity.Book;
import com.example.SLMS.entity.BookCopy;
import com.example.SLMS.exception.BookCopyMismatchException;
import com.example.SLMS.exception.BookNotFoundException;
import com.example.SLMS.exception.DuplicateBookIsbnException;
import com.example.SLMS.exception.DuplicateBookTitleException;
import com.example.SLMS.mapper.BookCopyMapper;
import com.example.SLMS.mapper.BookMapper;
import com.example.SLMS.repository.BookCopyRepository;
import com.example.SLMS.repository.BookRepository;
import com.example.SLMS.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final BookCopyRepository bookCopyRepository;
    private final BookMapper bookMapper;
    private final BookCopyMapper bookCopyMapper;

    public BookServiceImpl(BookRepository bookRepository, BookCopyRepository bookCopyRepository, BookMapper bookMapper, BookCopyMapper bookCopyMapper) {
        this.bookRepository = bookRepository;
        this.bookCopyRepository = bookCopyRepository;
        this.bookMapper = bookMapper;
        this.bookCopyMapper = bookCopyMapper;
    }

    @Override
    @Transactional
    public BookResponse createBook(CreateBookRequest createBookRequest) {
        Book book = bookMapper.toEntity(createBookRequest);
        if (bookRepository.existsByTitle(book.getTitle())) {
            throw new DuplicateBookTitleException(book.getTitle());
        }

        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DuplicateBookIsbnException(book.getIsbn());
        }

        Book savedBook = bookRepository.save(book);
        logger.info("Created book with ID: {}", savedBook.getId());
        return bookMapper.toResponse(savedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public BookWithCopiesResponse getBookWithCopiesById(Long id) throws BookNotFoundException {
        Book book = findBookByIdOrThrow(id);
        return bookMapper.toResponseWithCopies(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookResponse> getAllBooks(Pageable pageable) {
        Page<Book> booksPage = bookRepository.findAll(pageable);
        return booksPage.map(bookMapper::toResponse);
    }

    @Override
    @Transactional
    public BookResponse updateBook(Long id, UpdateBookRequest updates) {
        Book book = findBookByIdOrThrow(id);

        if (updates.getTitle() != null && !book.getTitle().equals(updates.getTitle())) {
            if (bookRepository.existsByTitle(updates.getTitle())) {
                throw new DuplicateBookTitleException(updates.getTitle());
            }
        }

        if (updates.getIsbn() != null && !book.getIsbn().equals(updates.getIsbn())) {
            if (bookRepository.existsByIsbn(updates.getIsbn())) {
                throw new DuplicateBookIsbnException(updates.getIsbn());
            }
        }

        bookMapper.updateBookFromDto(updates, book);
        Book updatedBook = bookRepository.save(book);
        logger.info("Updated book with ID: {}", updatedBook.getId());
        return bookMapper.toResponse(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            logger.warn("Attempted to delete non-existent book with ID: {}", id);
            throw new BookNotFoundException(id);
        }
        bookRepository.deleteById(id);
        logger.info("Deleted book with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookCopyResponse> getAvailableCopiesOfABook(Long id) {
        List<BookCopy> bookCopies = bookCopyRepository.findAllByBookIdAndAvailable(id, true);
        return bookCopies.stream()
                .map(bookCopyMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public BookCopyResponse addBookCopy(Long id) {
        Book book = findBookByIdOrThrow(id);
        BookCopy bookCopy = new BookCopy(book, true);
        BookCopy savedBookCopy = bookCopyRepository.save(bookCopy);
        return bookCopyMapper.toResponse(savedBookCopy);
    }

    @Override
    @Transactional
    public BookCopyResponse updateCopyAvailability(Long bookId, Long bookCopyId, boolean available) {
        BookCopy copy = bookCopyRepository.findById(bookCopyId)
                .orElseThrow(() -> new BookNotFoundException(bookCopyId));
        if (!copy.getBook().getId().equals(bookId)) {
            throw new BookCopyMismatchException(bookCopyId, bookId);
        }

        copy.setAvailable(available);
        BookCopy updatedCopy = bookCopyRepository.save(copy);
        return bookCopyMapper.toResponse(updatedCopy);
    }

    private Book findBookByIdOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
