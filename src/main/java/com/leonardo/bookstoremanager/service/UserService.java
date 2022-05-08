package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.dto.MessageDTO;
import com.leonardo.bookstoremanager.dto.UserDTO;
import com.leonardo.bookstoremanager.entitys.User;
import com.leonardo.bookstoremanager.exception.UserAlreadyExistsException;
import com.leonardo.bookstoremanager.mapper.UserMapper;
import com.leonardo.bookstoremanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private static final UserMapper userMapper = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO){
        verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUserName());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);

        return creationMessage(createdUser);
    }

    public void delete(Long id){
        verifyAndGetUser(id);
        userRepository.deleteById(id);
    }

    private User verifyAndGetUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    private void verifyIfExists(String userEmail, String userName) {
        Optional<User> foundUser = userRepository.findByEmailOrUserName(userEmail, userName);
        if (foundUser.isPresent()){
            throw new UserAlreadyExistsException(userEmail, userName);
        }
    }

    private MessageDTO creationMessage(User createdUser) {
        String createdUserMessage = String.format("User %s with ID %s successfully created!", createdUser.getName(), createdUser.getId());
        return MessageDTO.builder()
                .message(createdUserMessage)
                .build();
    }
}
