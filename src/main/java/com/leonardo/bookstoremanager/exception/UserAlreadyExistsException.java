package com.leonardo.bookstoremanager.exception;

import javax.persistence.EntityExistsException;

public class UserAlreadyExistsException extends EntityExistsException {

    public UserAlreadyExistsException(String userEmail, String userName) {
        super(String.format("User with email %s or username %s already exists!", userEmail, userName));
    }
}
