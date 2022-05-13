package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.mapper.BookMapper;
import com.leonardo.bookstoremanager.repository.BooksRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private BookMapper bookMapper = BookMapper.INSTANCE;

    private BooksRepository booksRepository;

    private AuthorService authorService;

    private UserService userService;

    private PublisherService publisherService;



}
