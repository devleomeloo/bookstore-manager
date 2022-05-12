package com.leonardo.bookstoremanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDTO {

    private Long id;

    private String name;

    private String isbn;

    private Long pages;

    private Long chapters;

    private AuthorDTO authorId;

    private PublisherDTO publisherId;
}
