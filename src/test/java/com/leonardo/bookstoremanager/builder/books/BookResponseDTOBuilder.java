package com.leonardo.bookstoremanager.builder.books;

import com.leonardo.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.leonardo.bookstoremanager.dto.*;
import com.leonardo.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.leonardo.bookstoremanager.users.builder.UserDTOBuilder;
import lombok.Builder;

@Builder
public class BookResponseDTOBuilder {

    @Builder.Default
    private Long id =  1L;

    @Builder.Default
    private String name = "Book Store Manager";

    @Builder.Default
    private String isbn = "978-3-16-148410-0";

    @Builder.Default
    private Integer pages = 200;

    @Builder.Default
    private Integer chapters = 10;

    @Builder.Default
    private AuthorDTO author = AuthorDTOBuilder.builder().build().buildAuthorDTO();

    @Builder.Default
    private PublisherDTO publisher = PublisherDTOBuilder.builder().build().buildPublisherDTO();

    @Builder.Default
    private UserDTO userDTO = UserDTOBuilder.builder().build().buildUserDTO();

    public BookResponseDTO buildBookResponse(){
        return new BookResponseDTO(
                id,
                name,
                isbn,
                pages,
                chapters,
                author,
                publisher
        );
    }
}
