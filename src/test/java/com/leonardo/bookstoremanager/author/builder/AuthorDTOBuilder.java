package com.leonardo.bookstoremanager.author.builder;

import com.leonardo.bookstoremanager.dto.AuthorDTO;
import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Leonardo";

    @Builder.Default
    private final int age = 27;

    public AuthorDTO buildAuthorDTO(){

        return new AuthorDTO(id, name, age);
    }
}
