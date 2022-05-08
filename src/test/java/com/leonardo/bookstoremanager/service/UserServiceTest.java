package com.leonardo.bookstoremanager.service;

import com.leonardo.bookstoremanager.dto.MessageDTO;
import com.leonardo.bookstoremanager.dto.UserDTO;
import com.leonardo.bookstoremanager.entitys.User;
import com.leonardo.bookstoremanager.exception.UserAlreadyExistsException;
import com.leonardo.bookstoremanager.mapper.UserMapper;
import com.leonardo.bookstoremanager.repository.UserRepository;
import com.leonardo.bookstoremanager.users.builder.UserDTOBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp(){
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenNewUserIsInformedThenItShouldBeCreated(){
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        String expectedCreationMessage = "User Leo Test with ID 1 successfully created!";
        String email = expectedCreatedUser.getEmail();
        String userName = expectedCreatedUser.getUserName();

        when(userRepository.findByEmailOrUserName(email, userName))
                .thenReturn(Optional.empty());

        when(userRepository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);

        MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

        assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
    }

    @Test
    void whenExistsUserIsInformedThenItShouldBeCreated(){
        UserDTO expectedDuplicatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDuplicatedUser = userMapper.toModel(expectedDuplicatedUserDTO);
        String email = expectedDuplicatedUser.getEmail();
        String userName = expectedDuplicatedUser.getUserName();

        when(userRepository.findByEmailOrUserName(email, userName))
                .thenReturn(Optional.of(expectedDuplicatedUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(expectedDuplicatedUserDTO));
    }

    @Test
    void whenValidIdIsGivenThenItShouldBeDeleted() {
        UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDeletedUser = userMapper.toModel(expectedDeletedUserDTO);

        Long expectedDeletedUserId = expectedDeletedUser.getId();
        doNothing().when(userRepository).deleteById(expectedDeletedUserId);
        when(userRepository.findById(expectedDeletedUserId)).thenReturn(Optional.of(expectedDeletedUser));

        userService.delete(expectedDeletedUserId);

        verify(userRepository, times(1)).deleteById(expectedDeletedUserId);
        verify(userRepository, times(1)).findById(expectedDeletedUserId);
    }

    @Test
    void whenInvalidUserIdIsGivenThenAnExceptionShouldBeThrow() {
        var expectedInvalidUserId = 2L;

        when(userRepository.findById(expectedInvalidUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(expectedInvalidUserId));
    }

    @Test
    void whenExistingUserIsInformedThenItShouldBeUpdated(){
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);
        String expectedUpdateMessage = "User Leo Test with ID 1 successfully updated!";

        when(userRepository.findById(expectedUpdatedUserDTO.getId()))
                .thenReturn(Optional.of(expectedUpdatedUser));
        when(userRepository.save(expectedUpdatedUser))
                .thenReturn(expectedUpdatedUser);

        MessageDTO updatedMessage = userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO);

        assertThat(expectedUpdateMessage, is(equalTo(updatedMessage.getMessage())));
    }

    @Test
    void whenNotExistingUserIsInformedThenAnExceptionShouldBeThrown(){
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);

        when(userRepository.findById(expectedUpdatedUser.getId()))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                userService.update(expectedUpdatedUser.getId(), expectedUpdatedUserDTO));
    }
}
