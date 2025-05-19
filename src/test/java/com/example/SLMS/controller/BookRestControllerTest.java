package com.example.SLMS.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.SLMS.controller.api.BookRestController;
import com.example.SLMS.dto.request.CreateBookRequest;
import com.example.SLMS.dto.response.BookCopyResponse;
import com.example.SLMS.dto.response.BookResponse;
import com.example.SLMS.dto.response.BookWithCopiesResponse;
import com.example.SLMS.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

public class BookRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookRestController bookRestController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookRestController).build();
    }

    @Test
    public void testCreateBook() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn("1234567891011");
        createBookRequest.setPublishedYear(2022);

        BookResponse bookResponse = new BookResponse();
        bookResponse.setId(1L);
        bookResponse.setTitle("Test Book");
        bookResponse.setAuthor("John Doe");
        bookResponse.setIsbn("1234567891011");
        bookResponse.setPublishedYear(2022);

        when(bookService.createBook(any(CreateBookRequest.class))).thenReturn(bookResponse);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Test Book"))
                .andExpect(jsonPath("$.author").value("John Doe"))
                .andExpect(jsonPath("$.isbn").value("1234567891011"))
                .andExpect(jsonPath("$.publishedYear").value(2022));

        Mockito.verify(bookService).createBook(any(CreateBookRequest.class));
    }

    @Test
    public void testCreateBook_InvalidIsbn() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn("fsdjfsd312j");
        createBookRequest.setPublishedYear(2022);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_TooShortIsbn() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn("123456789101");
        createBookRequest.setPublishedYear(2022);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_TooLongIsbn() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn("123456789101");
        createBookRequest.setPublishedYear(2022);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_InvalidPublishYear() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn("1234567891011");
        createBookRequest.setPublishedYear(-109);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_MissingTitle() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle(null);
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn("1234567891011");
        createBookRequest.setPublishedYear(2022);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_MissingAuthor() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor(null);
        createBookRequest.setIsbn("1234567891011");
        createBookRequest.setPublishedYear(2022);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_MissingIsbn() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn(null);
        createBookRequest.setPublishedYear(2022);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_MissingPublishYear() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn("1234567891011");
        createBookRequest.setPublishedYear(null);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_EmptyTitle() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("");
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn("1234567891011");
        createBookRequest.setPublishedYear(2022);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_EmptyAuthor() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor("");
        createBookRequest.setIsbn("1234567891011");
        createBookRequest.setPublishedYear(2022);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBook_EmptyIsbn() throws Exception {
        CreateBookRequest createBookRequest = new CreateBookRequest();
        createBookRequest.setTitle("Test Book");
        createBookRequest.setAuthor("John Doe");
        createBookRequest.setIsbn("");
        createBookRequest.setPublishedYear(2022);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createBookRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
    public void testGetBookById() throws Exception {
        Long bookId = 1L;
        String bookTitle = "Test Book";
        String bookAuthor = "Test Book";
        String bookIsbn = "1234567891011";
        Integer bookPublishYear = 2022;

        BookCopyResponse bookCopyResponseFirst = new BookCopyResponse();
        bookCopyResponseFirst.setId(1L);
        bookCopyResponseFirst.setAvailable(true);

        BookCopyResponse bookCopyResponseSecond = new BookCopyResponse();
        bookCopyResponseSecond.setId(2L);
        bookCopyResponseSecond.setAvailable(true);

        List<BookCopyResponse> copiesResponse = Arrays.asList(bookCopyResponseFirst, bookCopyResponseSecond);

        BookWithCopiesResponse response = new BookWithCopiesResponse();
        response.setId(bookId);
        response.setTitle(bookTitle);
        response.setAuthor(bookAuthor);
        response.setIsbn(bookIsbn);
        response.setPublishedYear(bookPublishYear);
        response.setCopies(copiesResponse);

        when(bookService.getBookWithCopiesById(bookId)).thenReturn(response);

        mockMvc.perform(get("/api/books/{id}", bookId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(bookId))
                .andExpect(jsonPath("$.title").value(bookTitle))
                .andExpect(jsonPath("$.author").value(bookAuthor))
                .andExpect(jsonPath("$.isbn").value(bookIsbn))
                .andExpect(jsonPath("$.publishedYear").value(bookPublishYear))

                .andExpect(jsonPath("$.copies.length()").value(2))
                .andExpect(jsonPath("$.copies[0].id").value(1))
                .andExpect(jsonPath("$.copies[0].available").value(true))
                .andExpect(jsonPath("$.copies[1].id").value(2))
                .andExpect(jsonPath("$.copies[1].available").value(true));

        Mockito.verify(bookService).getBookWithCopiesById(bookId);
    }

}
