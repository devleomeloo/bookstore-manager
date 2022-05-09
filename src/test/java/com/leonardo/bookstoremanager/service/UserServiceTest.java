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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
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

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenPOSTUserIsInformedThenItShouldBeCreated(){
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        String expectedCreationMessage = "User Leo Test with ID 1 successfully created!";
        String email = expectedCreatedUser.getEmail();
        String userName = expectedCreatedUser.getUserName();

        when(userRepository.findByEmailOrUserName(email, userName))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(expectedCreatedUser.getPassword()))
                .thenReturn(expectedCreatedUser.getPassword());
        when(userRepository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);

        MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

        assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
    }

    @Test
    void whenPOSTExistsUserIsInformedThenAnExceptionShouldBeThrow(){
        UserDTO expectedDuplicatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDuplicatedUser = userMapper.toModel(expectedDuplicatedUserDTO);
        String email = expectedDuplicatedUser.getEmail();
        String userName = expectedDuplicatedUser.getUserName();

        when(userRepository.findByEmailOrUserName(email, userName))
                .thenReturn(Optional.of(expectedDuplicatedUser));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(expectedDuplicatedUserDTO));
    }

    @Test
    void whenUPDATEExistingUserIsInformedThenItShouldBeUpdated(){
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);
        String expectedUpdateMessage = "User Leo Test with ID 1 successfully updated!";

        when(userRepository.findById(expectedUpdatedUserDTO.getId()))
                .thenReturn(Optional.of(expectedUpdatedUser));
        when(passwordEncoder.encode(expectedUpdatedUser.getPassword()))
                .thenReturn(expectedUpdatedUser.getPassword());
        when(userRepository.save(expectedUpdatedUser))
                .thenReturn(expectedUpdatedUser);

        MessageDTO updatedMessage = userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO);

        assertThat(expectedUpdateMessage, is(equalTo(updatedMessage.getMessage())));
    }

    @Test
    void whenUPDATENotExistingUserIsInformedThenAnExceptionShouldBeThrown(){
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);
        Long id = expectedUpdatedUser.getId();

        when(userRepository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () ->
                userService.update(id, expectedUpdatedUserDTO));
    }

    @Test
    void whenGETExistingUserIsInformedThenItBeFound(){
        UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();
        User expectedFoundUser = userMapper.toModel(expectedFoundUserDTO);

        when(userRepository.findById(expectedFoundUser.getId()))
                .thenReturn(Optional.of(expectedFoundUser));

        UserDTO foundUser = userService.findById(expectedFoundUserDTO.getId());

        assertThat(foundUser, is(equalTo(expectedFoundUserDTO)));

    }

    @Test
    void whenGETAllUsersIsInformedThenItBeFound(){
        List<UserDTO> expectedFoundUserDTO = List.of(userDTOBuilder.buildUserDTO());
        User expectedFoundUser = userMapper.toModel(expectedFoundUserDTO.get(0));

        when(userRepository.findAll())
                .thenReturn(Collections.singletonList(expectedFoundUser));

        List<UserDTO> foundUsers = userService.findAll();

        assertThat(foundUsers, is(equalTo(expectedFoundUserDTO)));

    }

    @Test
    void whenDELETEValidIdIsGivenThenItShouldBeDeleted() {
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
    void whenDELETEInvalidUserIdIsGivenThenAnExceptionShouldBeThrow() {
        var expectedInvalidUserId = 2L;

        when(userRepository.findById(expectedInvalidUserId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.delete(expectedInvalidUserId));
    }

}
