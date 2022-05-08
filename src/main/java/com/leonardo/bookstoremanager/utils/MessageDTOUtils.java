package com.leonardo.bookstoremanager.utils;

import com.leonardo.bookstoremanager.dto.MessageDTO;
import com.leonardo.bookstoremanager.entitys.User;

public class MessageDTOUtils {

    public static MessageDTO creationMessage(User createdUser) {
        return returnMessage(createdUser,"created");
    }

    public static MessageDTO updatedMessage(User updatedUser) {
        return returnMessage(updatedUser,"updated");
    }

    public static MessageDTO returnMessage(User updatedUser, String action) {
        String createdUserMessage = String.format("User %s with ID %s successfully %s!",
                updatedUser.getName(),
                updatedUser.getId(),
                action);

        return MessageDTO.builder()
                .message(createdUserMessage)
                .build();
    }
}
