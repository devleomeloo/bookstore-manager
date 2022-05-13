package com.leonardo.bookstoremanager.service;

import books.builder.BookRequestDTOBuilder;
import books.builder.BookResponseDTOBuilder;
import com.leonardo.bookstoremanager.dto.AuthenticatedUser;
import com.leonardo.bookstoremanager.mapper.BookMapper;
import com.leonardo.bookstoremanager.repository.BooksRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private BookMapper bookMapper = BookMapper.INSTANCE;

    @Mock
    private BooksRepository booksRepository;

    @Mock
    private AuthorService authorService;

    @Mock
    private UserService userService;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private BookService bookService;

    private BookRequestDTOBuilder bookRequestDTOBuilder;

    private BookResponseDTOBuilder bookResponseDTOBuilder;

    private AuthenticatedUser authenticatedUser;

    @BeforeEach
    void setUp() {
        bookRequestDTOBuilder = BookRequestDTOBuilder.builder().build();
        bookResponseDTOBuilder = BookResponseDTOBuilder.builder().build();
        authenticatedUser = new AuthenticatedUser("leoMelo", "123456", "ADMIN");
    }
}
