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

import static com.leonardo.bookstoremanager.utils.JsonConversionUtils.asPublisherJsonString;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class PublisherControllerTest {

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
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        expectedCreatedPublisherDTO.setName(null);

        mockMvc.perform(post(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asPublisherJsonString(expectedCreatedPublisherDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETWithValidIdIsCalledThenOkStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedFoundPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        when(publisherService.findById(expectedFoundPublisherDTO.getId()))
                .thenReturn(expectedFoundPublisherDTO);

        mockMvc.perform(get(PUBLISHER_API_URL_PATH + "/" + expectedFoundPublisherDTO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedFoundPublisherDTO.getId().intValue())))
                .andExpect(jsonPath("$.name", is(expectedFoundPublisherDTO.getName())))
                .andExpect(jsonPath("$.code", is(expectedFoundPublisherDTO.getCode())))
                .andExpect(jsonPath("$.foundationDate", is(expectedFoundPublisherDTO.getFoundationDate())));

    }
}
