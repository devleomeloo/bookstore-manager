package com.leonardo.bookstoremanager.mapper;

import com.leonardo.bookstoremanager.dto.AuthorDTO;
import com.leonardo.bookstoremanager.entitys.Author;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    Author toModel(AuthorDTO authorDTO);

    AuthorDTO toDTO(Author author);
}
