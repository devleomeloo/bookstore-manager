package com.leonardo.bookstoremanager.service;

import javax.persistence.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long id) {
        super(String.format("User with id %s not exists!", id));
    }

    public UserNotFoundException(String username) {
        super(String.format("User with name %s not exists!", username));
    }
}
