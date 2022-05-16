package com.leonardo.bookstoremanager.mapper;

import com.leonardo.bookstoremanager.dto.BookRequestDTO;
import com.leonardo.bookstoremanager.dto.BookResponseDTO;
import com.leonardo.bookstoremanager.entitys.Book;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    Book toModel(BookRequestDTO bookRequestDTO);

    Book toModel(BookResponseDTO bookRequestDTO);

    BookResponseDTO toDTO(Book book);
}
