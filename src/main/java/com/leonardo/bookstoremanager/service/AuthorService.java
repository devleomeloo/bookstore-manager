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

@Service
public class AuthorService {

    private static final  AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    private final AuthorRepository authorRepository;


    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO){

        verifyIfExists(authorDTO.getName());

        Author authorToCreate = authorMapper.toModel(authorDTO);
        Author createdAuthor = authorRepository.save(authorToCreate);

        return authorMapper.toDTO(createdAuthor);
    }

    public AuthorDTO findById(Long id){
        return authorMapper.toDTO(verifyAndGetIfExists(id));
    }

    public List<AuthorDTO> findAll(){
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toDTO)
                .toList();
    }

    public void delete(Long id){
        verifyAndGetIfExists(id);
        authorRepository.deleteById(id);
    }

    public Author verifyAndGetIfExists(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    private void verifyIfExists(String authorName) {
        authorRepository.findByName(authorName)
                .ifPresent(author -> {throw new AuthorAlreadyExistsException(authorName); });
    }
}
