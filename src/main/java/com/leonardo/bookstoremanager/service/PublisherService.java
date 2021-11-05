package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.dto.PublisherDTO;
import com.leonardo.bookstoremanager.entitys.Publisher;
import com.leonardo.bookstoremanager.mapper.PublisherMapper;
import com.leonardo.bookstoremanager.repository.PublisherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final static PublisherMapper publisherMapper = PublisherMapper.INSTANCE;

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }

    public PublisherDTO create(PublisherDTO publisherDTO){
        Publisher publisherToCreate = publisherMapper.INSTANCE.toModel(publisherDTO);
        Publisher createdPublisher = publisherRepository.save(publisherToCreate);

        return publisherMapper.toDTO(createdPublisher);
    }
}
