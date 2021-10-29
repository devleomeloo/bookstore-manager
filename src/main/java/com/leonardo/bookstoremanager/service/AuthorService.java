package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.dto.AuthorDTO;
import com.leonardo.bookstoremanager.entitys.Author;
import com.leonardo.bookstoremanager.exception.AuthorAlreadyExistsException;
import com.leonardo.bookstoremanager.exception.AuthorNotFoundException;
import com.leonardo.bookstoremanager.mapper.AuthorMapper;
import com.leonardo.bookstoremanager.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorService {

    private final static AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO){

        verifyIfExists(authorDTO.getName());

        Author authorToCreate = authorMapper.INSTANCE.toModel(authorDTO);
        Author createdAuthor = authorRepository.save(authorToCreate);

        return authorMapper.toDTO(createdAuthor);
    }

    public AuthorDTO findById(Long id){
        Author foundAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));

        return authorMapper.toDTO(foundAuthor);
    }

    public List<AuthorDTO> findAll(){
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .collect(Collectors.toList());
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> {throw new AuthorAlreadyExistsException(authorName); });
    }
}
