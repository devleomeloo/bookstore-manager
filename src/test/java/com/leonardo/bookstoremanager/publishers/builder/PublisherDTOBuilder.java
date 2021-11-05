package com.leonardo.bookstoremanager.publishers.builder;

import com.leonardo.bookstoremanager.dto.PublisherDTO;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;


    private final String name = "Mellos Editora";

    private final String code = "Mellos1910";

    private final LocalDate foundationDate = LocalDate.of(2021,11,5);

    public PublisherDTO buildPublisherDTO(){
        return new PublisherDTO(
                id,
                name,
                code,
                foundationDate);
    }
}
