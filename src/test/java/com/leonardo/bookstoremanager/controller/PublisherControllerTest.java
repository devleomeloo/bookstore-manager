package com.leonardo.bookstoremanager.controller;

import com.leonardo.bookstoremanager.dto.PublisherDTO;
import com.leonardo.bookstoremanager.publishers.builder.PublisherDTOBuilder;
import com.leonardo.bookstoremanager.service.PublisherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;

import static com.leonardo.bookstoremanager.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PublisherControllerTest {

    private final static String PUBLISHER_API_URL_PATH = "/api/v1/publishers";
    private MockMvc mockMvc;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherController;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(publisherController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }

    @Test
    void whenPOSTIsCalledThenSuccessStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        mockMvc.perform(post(PUBLISHER_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        expectedCreatedPublisherDTO.setName(null);

        mockMvc.perform(post(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETWithValidIdIsCalledThenOkStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        Long expectedCreatedPublisherDTOId = expectedCreatedPublisherDTO.getId();

        when(publisherService.findById(expectedCreatedPublisherDTOId))
                .thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(get(PUBLISHER_API_URL_PATH + "/" + expectedCreatedPublisherDTOId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedCreatedPublisherDTOId.intValue())))
                .andExpect(jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenGETListIsCalledThenOkStatusShouldBeInformed () throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        when(publisherService.findAll()).thenReturn(Collections.singletonList(expectedCreatedPublisherDTO));

        mockMvc.perform(get(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(expectedCreatedPublisherDTO.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(expectedCreatedPublisherDTO.getName())))
                .andExpect(jsonPath("$[0].code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenDELETEPublisherIsCalledThenNoContentShouldBeInformed() throws Exception {
        PublisherDTO expectedDeletedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        doNothing().when(publisherService).delete(expectedDeletedPublisherDTO.getId());

        mockMvc.perform(delete(PUBLISHER_API_URL_PATH + "/" + expectedDeletedPublisherDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
