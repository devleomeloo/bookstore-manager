package com.leonardo.bookstoremanager.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.leonardo.bookstoremanager.dto.AuthorDTO;
import com.leonardo.bookstoremanager.dto.PublisherDTO;

public class JsonConversionUtils {

    public static String asJsonString(AuthorDTO expectedCreatedAuthorDTO) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModules(new JavaTimeModule());

            return objectMapper.writeValueAsString(expectedCreatedAuthorDTO);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String asPublisherJsonString(PublisherDTO expectedCreatedPublisherDTO) {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            objectMapper.registerModules(new JavaTimeModule());

            return objectMapper.writeValueAsString(expectedCreatedPublisherDTO);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
