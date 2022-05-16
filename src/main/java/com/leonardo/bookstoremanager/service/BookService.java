package com.leonardo.bookstoremanager.service;

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
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookMapper bookMapper = BookMapper.INSTANCE;

    private BookRepository bookRepository;

    private AuthorService authorService;

    private UserService userService;

    private PublisherService publisherService;

    public BookResponseDTO create(AuthenticatedUser authenticatedUser, BookRequestDTO bookRequestDTO) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        verifyIfBookIsAlreadyRegistered(foundAuthenticatedUser, bookRequestDTO);

        Author foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        Book bookToSave = bookMapper.toModel(bookRequestDTO);
        bookToSave.setUser(foundAuthenticatedUser);
        bookToSave.setAuthor(foundAuthor);
        bookToSave.setPublisher(foundPublisher);
        Book savedBook = bookRepository.save(bookToSave);

        return bookMapper.toDTO(savedBook);
    }

    public BookResponseDTO findByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .map(bookMapper::toDTO)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public List<BookResponseDTO> findAllByUser(AuthenticatedUser authenticatedUser) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        return bookRepository.findAllByUser(foundAuthenticatedUser)
                .stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());

    }

    private void verifyIfBookIsAlreadyRegistered(User foundUser, BookRequestDTO bookRequestDTO) {
        bookRepository.findByNameAndIsbnAndUser(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser)
                .ifPresent(duplicatedBook -> {
                    throw new BookAlreadyExistsException(bookRequestDTO.getName(), bookRequestDTO.getIsbn(), foundUser.getUserName());
                });
    }

    @Transactional
    public void deleteByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        Book foundBookToDelete = verifyAndGetIfExists(bookId, foundAuthenticatedUser);
        bookRepository.deleteByIdAndUser(foundBookToDelete.getId(), foundAuthenticatedUser);
    }

    private Book verifyAndGetIfExists(Long bookId, User foundAuthenticatedUser) {
        return bookRepository.findByIdAndUser(bookId, foundAuthenticatedUser)
                .orElseThrow(() -> new BookNotFoundException(bookId));
    }

    public BookResponseDTO updateByIdAndUser(AuthenticatedUser authenticatedUser, Long bookId, BookRequestDTO bookRequestDTO) {
        User foundAuthenticatedUser = userService.verifyAndGetUserIfExists(authenticatedUser.getUsername());
        Book foundBook = verifyAndGetIfExists(bookId, foundAuthenticatedUser);
        Author foundAuthor = authorService.verifyAndGetIfExists(bookRequestDTO.getAuthorId());
        Publisher foundPublisher = publisherService.verifyAndGetIfExists(bookRequestDTO.getPublisherId());

        Book bookToUpdate = bookMapper.toModel(bookRequestDTO);
        bookToUpdate.setId(foundBook.getId());
        bookToUpdate.setUser(foundAuthenticatedUser);
        bookToUpdate.setAuthor(foundAuthor);
        bookToUpdate.setPublisher(foundPublisher);
        bookToUpdate.setCreatedDate(foundBook.getCreatedDate());
        Book updatedBook = bookRepository.save(bookToUpdate);
        return bookMapper.toDTO(updatedBook);
    }


}
