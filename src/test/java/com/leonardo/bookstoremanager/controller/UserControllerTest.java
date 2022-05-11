package com.leonardo.bookstoremanager.controller;

import com.leonardo.bookstoremanager.authentication.builder.JwtRequestBuilder;
import com.leonardo.bookstoremanager.dto.JwtRequest;
import com.leonardo.bookstoremanager.dto.JwtResponse;
import com.leonardo.bookstoremanager.dto.MessageDTO;
import com.leonardo.bookstoremanager.dto.UserDTO;
import com.leonardo.bookstoremanager.service.AuthenticationService;
import com.leonardo.bookstoremanager.service.UserService;
import com.leonardo.bookstoremanager.users.builder.UserDTOBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.leonardo.bookstoremanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private static final String USER_API_URL_PATH = "/api/v1/users";

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private UserDTOBuilder userDTOBuilder;

    private JwtRequestBuilder jwtRequestBuilder;


    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
        jwtRequestBuilder = JwtRequestBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
        String expectedCreationMessage = "User Leo Test with ID 1 successfully created!";
        MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(userService.create(expectedUserToCreateDTO)).thenReturn(expectedCreationMessageDTO);

        mockMvc.perform(post(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToCreateDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is(expectedCreationMessage)));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
        expectedUserToCreateDTO.setUserName(null);

        mockMvc.perform(post(USER_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedUserToCreateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenUPDATEIsCalledThenUpdatedStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToUpdatedDTO = userDTOBuilder.buildUserDTO();
        String expectedUpdatedMessage = "User Leo Test with ID 1 successfully updated!";
        MessageDTO expectedUpdatedMessageDTO = MessageDTO.builder().message(expectedUpdatedMessage).build();

        when(userService.update(expectedUserToUpdatedDTO.getId(), expectedUserToUpdatedDTO))
                .thenReturn(expectedUpdatedMessageDTO);

        mockMvc.perform(MockMvcRequestBuilders.put(USER_API_URL_PATH + "/" + expectedUserToUpdatedDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedUserToUpdatedDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(expectedUpdatedMessage)));
    }

    @Test
    void whenUPDATEDIsCalledWithoutRequiredFieldThenBadRequestStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToUpdateDTO = userDTOBuilder.buildUserDTO();
        expectedUserToUpdateDTO.setUserName(null);

        mockMvc.perform(MockMvcRequestBuilders.put(USER_API_URL_PATH + "/" + expectedUserToUpdateDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedUserToUpdateDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETWithIdIsCalledThenStatusOkShouldBeReturned() throws Exception {

        UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();

        when(userService.findById(expectedFoundUserDTO.getId()))
                .thenReturn(expectedFoundUserDTO);

        mockMvc.perform(get(USER_API_URL_PATH + "/" + expectedFoundUserDTO.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenGETAllUsersIsCalledThenStatusOkShouldBeReturned() throws Exception {
        UserDTO expectedFoundUserDTO = userDTOBuilder.buildUserDTO();

        when(userService.findAll())
                .thenReturn(Collections.singletonList(expectedFoundUserDTO));

        mockMvc.perform(get(USER_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    void whenDELETEWithValidIdIsCalledThenNoContentShouldBeReturned() throws Exception {
        UserDTO expectedUserDeletedDTO = userDTOBuilder.buildUserDTO();

        var expectedUserDeletedId = expectedUserDeletedDTO.getId();
        doNothing().when(userService).delete(expectedUserDeletedId);

        mockMvc.perform(delete(USER_API_URL_PATH + "/" + expectedUserDeletedId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

    }

    @Test
    void whenPOSTIsCalledToAuthenticateUserThenOkShouldBeReturned() throws Exception {
        JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
        JwtResponse expectedJwtToken = JwtResponse.builder().jwtToken("fakeToken").build();

        when(authenticationService.createAuthenticationToken(jwtRequest))
                .thenReturn(expectedJwtToken);

        mockMvc.perform(post(USER_API_URL_PATH + "/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jwtRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jwtToken", is(expectedJwtToken.getJwtToken())));
    }

    @Test
    void whenPOSTIsCalledToAuthenticateUserWithoutPasswordFieldThenAnBadRequestShouldBeReturned() throws Exception {
        JwtRequest jwtRequest = jwtRequestBuilder.buildJwtRequest();
        jwtRequest.setPassword(null);

        mockMvc.perform(post(USER_API_URL_PATH + "/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(jwtRequest)))
                .andExpect(status().isBadRequest());
    }
}