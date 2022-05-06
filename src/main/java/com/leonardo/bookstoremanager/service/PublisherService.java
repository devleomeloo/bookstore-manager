package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.dto.PublisherDTO;
import com.leonardo.bookstoremanager.entitys.Publisher;
import com.leonardo.bookstoremanager.exception.PublisherAlreadyExistsException;
import com.leonardo.bookstoremanager.exception.PublisherNotFoundException;
import com.leonardo.bookstoremanager.mapper.PublisherMapper;
import com.leonardo.bookstoremanager.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublisherService {

    private static final PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private final PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }


    public PublisherDTO create(PublisherDTO publisherDTO){

        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());

        Publisher publisherToCreate = publisherMapper.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);

        return publisherMapper.toDTO(createdPublisher);
    }

    public PublisherDTO findById(Long id){
         return publisherRepository.findById(id)
                 .map(publisherMapper::toDTO)
                 .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    public List<PublisherDTO> findAll(){
        return publisherRepository.findAll()
                .stream()
                .map(publisherMapper::toDTO)
                .toList();
    }

    public void delete(Long id){
        verifyIfExists(id);
        publisherRepository.deleteById(id);
    }

    private void verifyIfExists(Long id) {
        publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id));
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);
        if (duplicatedPublisher.isPresent()){
            throw new PublisherAlreadyExistsException(name, code);
        }
    }
}
