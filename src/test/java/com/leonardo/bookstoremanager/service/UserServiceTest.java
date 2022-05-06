package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.mapper.UserMapper;
import com.leonardo.bookstoremanager.repository.UserRepository;
import com.leonardo.bookstoremanager.users.builder.UserDTOBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp(){
        UserDTOBuilder userDTOBuilder = UserDTOBuilder.builder().build();
    }
}
