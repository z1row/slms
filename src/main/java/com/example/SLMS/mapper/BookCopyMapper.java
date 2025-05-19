package com.example.SLMS.mapper;

import com.example.SLMS.dto.response.BookCopyResponse;
import com.example.SLMS.entity.BookCopy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookCopyMapper {
    BookCopyResponse toResponse(BookCopy bookCopy);
}
