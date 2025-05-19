package com.example.SLMS.mapper;

import com.example.SLMS.dto.request.CreateBookRequest;
import com.example.SLMS.dto.request.UpdateBookRequest;
import com.example.SLMS.dto.response.BookResponse;
import com.example.SLMS.dto.response.BookWithCopiesResponse;
import com.example.SLMS.entity.Book;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = BookCopyMapper.class)
public interface BookMapper {
    BookResponse toResponse(Book book);
    BookWithCopiesResponse toResponseWithCopies(Book book);
    Book toEntity(CreateBookRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromDto(UpdateBookRequest updateBookRequest, @MappingTarget Book book);
}
