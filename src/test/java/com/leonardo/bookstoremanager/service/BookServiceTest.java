package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.builder.books.BookRequestDTOBuilder;
import com.leonardo.bookstoremanager.builder.books.BookResponseDTOBuilder;
import com.leonardo.bookstoremanager.dto.AuthenticatedUser;
import com.leonardo.bookstoremanager.dto.BookRequestDTO;
import com.leonardo.bookstoremanager.dto.BookResponseDTO;
import com.leonardo.bookstoremanager.entitys.Author;
import com.leonardo.bookstoremanager.entitys.Book;
import com.leonardo.bookstoremanager.entitys.Publisher;
import com.leonardo.bookstoremanager.entitys.User;
import com.leonardo.bookstoremanager.exception.BookAlreadyExistsException;
import com.leonardo.bookstoremanager.exception.BookNotFoundException;
import com.leonardo.bookstoremanager.mapper.BookMapper;
import com.leonardo.bookstoremanager.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserService userService;

    @Mock
    private AuthorService authorService;

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
        authenticatedUser = new AuthenticatedUser("rodrigo", "123456", "ADMIN");
    }

    @Test
    void whenNewBookIsInformedThenItShouldBeCreated() {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedCreatedBook = bookMapper.toModel(expectedCreatedBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class))).thenReturn(Optional.empty());
        when(authorService.verifyAndGetIfExists(expectedBookToCreateDTO.getAuthorId())).thenReturn(new Author());
        when(publisherService.verifyAndGetIfExists(expectedBookToCreateDTO.getPublisherId())).thenReturn(new Publisher());
        when(bookRepository.save(any(Book.class))).thenReturn(expectedCreatedBook);

        BookResponseDTO createdBookResponseDTO = bookService.create(authenticatedUser, expectedBookToCreateDTO);

        assertThat(createdBookResponseDTO, is(equalTo(expectedCreatedBookDTO)));
    }

    @Test
    void whenExistingBookIsInformedToCreateThenAnExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToCreateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedCreatedBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedDuplicatedBook = bookMapper.toModel(expectedCreatedBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByNameAndIsbnAndUser(
                eq(expectedBookToCreateDTO.getName()),
                eq(expectedBookToCreateDTO.getIsbn()),
                any(User.class))).thenReturn(Optional.of(expectedDuplicatedBook));

        assertThrows(BookAlreadyExistsException.class, () -> bookService.create(authenticatedUser, expectedBookToCreateDTO));
    }

    @Test
    void whenExistingBookIsInformedThenABookShouldBeReturned() {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class))).thenReturn(Optional.of(expectedFoundBook));

        BookResponseDTO foundBookDTO = bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId());

        assertThat(foundBookDTO, is(equalTo(expectedFoundBookDTO)));
    }

    @Test
    void whenNotExistingBookIsInformedThenAnExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToFindDTO = bookRequestDTOBuilder.buildRequestBookDTO();

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(
                eq(expectedBookToFindDTO.getId()),
                any(User.class))).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.findByIdAndUser(authenticatedUser, expectedBookToFindDTO.getId()));
    }

    @Test
    void whenListBookIsCalledThenItShouldBeReturned() {
        BookResponseDTO expectedFoundBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedFoundBook = bookMapper.toModel(expectedFoundBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(
                any(User.class))).thenReturn(Collections.singletonList(expectedFoundBook));

        List<BookResponseDTO> returnedBooksResponseList = bookService.findAllByUser(authenticatedUser);

        assertThat(returnedBooksResponseList.size(), is(1));
        assertThat(returnedBooksResponseList.get(0), is(equalTo(expectedFoundBookDTO)));
    }

    @Test
    void whenListBookIsCalledThenAnEmptyListItShouldBeReturned() {
        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findAllByUser(any(User.class))).thenReturn(Collections.EMPTY_LIST);

        List<BookResponseDTO> returnedBooksResponseList = bookService.findAllByUser(authenticatedUser);

        assertThat(returnedBooksResponseList.size(), is(0));
    }

    @Test
    void whenExistingBookIdIsInformedThenItShouldBeDeleted() {
        BookResponseDTO expectedBookToDeleteDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedBookToDelete = bookMapper.toModel(expectedBookToDeleteDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookToDeleteDTO.getId()), any(User.class)))
                .thenReturn(Optional.of(expectedBookToDelete));
        doNothing().when(bookRepository).deleteByIdAndUser(eq(expectedBookToDeleteDTO.getId()), any(User.class));

        bookService.deleteByIdAndUser(authenticatedUser, expectedBookToDeleteDTO.getId());

        verify(bookRepository, times(1)).deleteByIdAndUser(eq(expectedBookToDeleteDTO.getId()), any(User.class));
    }

    @Test
    void whenNotExistingBookIdIsInformedThenAnExceptionShouldBeThrown() {
        BookResponseDTO expectedBookToDeleteDTO = bookResponseDTOBuilder.buildBookResponse();

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookToDeleteDTO.getId()), any(User.class)))
                .thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.deleteByIdAndUser(authenticatedUser, expectedBookToDeleteDTO.getId()));
    }

    @Test
    void whenExistingBookIdIsInformedThenItShouldBeUpdated() {
        BookRequestDTO expectedBookToUpdateDTO = bookRequestDTOBuilder.buildRequestBookDTO();
        BookResponseDTO expectedUpdatedBookDTO = bookResponseDTOBuilder.buildBookResponse();
        Book expectedUpdatedBook = bookMapper.toModel(expectedUpdatedBookDTO);

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookToUpdateDTO.getId()), any(User.class)))
                .thenReturn(Optional.of(expectedUpdatedBook));
        when(authorService.verifyAndGetIfExists(expectedBookToUpdateDTO.getAuthorId())).thenReturn(new Author());
        when(publisherService.verifyAndGetIfExists(expectedBookToUpdateDTO.getPublisherId())).thenReturn(new Publisher());
        when(bookRepository.save(any(Book.class))).thenReturn(expectedUpdatedBook);

        BookResponseDTO updatedBookResponseDTO = bookService.updateByIdAndUser(
                authenticatedUser,
                expectedBookToUpdateDTO.getId(),
                expectedBookToUpdateDTO);

        assertThat(updatedBookResponseDTO, is(equalTo(expectedUpdatedBookDTO)));
    }

    @Test
    void whenNotExistingBookIdIsInformedThennExceptionShouldBeThrown() {
        BookRequestDTO expectedBookToUpdateDTO = bookRequestDTOBuilder.buildRequestBookDTO();

        when(userService.verifyAndGetUserIfExists(authenticatedUser.getUsername())).thenReturn(new User());
        when(bookRepository.findByIdAndUser(eq(expectedBookToUpdateDTO.getId()), any(User.class)))
                .thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> bookService.updateByIdAndUser(
                authenticatedUser,
                expectedBookToUpdateDTO.getId(),
                expectedBookToUpdateDTO));
    }
}

