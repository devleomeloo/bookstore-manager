package com.leonardo.bookstoremanager.mapper;

import com.leonardo.bookstoremanager.dto.PublisherDTO;
import com.leonardo.bookstoremanager.entitys.Publisher;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PublisherMapper {

    PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    Publisher toModel(PublisherDTO publisherDTO);

    PublisherDTO toDTO(Publisher publisher);
}
