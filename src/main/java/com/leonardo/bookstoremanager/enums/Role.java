package com.leonardo.bookstoremanager.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    ADMIN("Admin"),
    USER("User");

    private final String description;
}
