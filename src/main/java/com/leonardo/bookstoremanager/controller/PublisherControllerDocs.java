package com.leonardo.bookstoremanager.controller;

import com.leonardo.bookstoremanager.dto.PublisherDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api("Publishers Management")
public interface PublisherControllerDocs {

    @ApiOperation(value = "Publisher creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success - Publisher created"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or author already registered on system")
    })
    PublisherDTO create(PublisherDTO publisherDTO);

    @ApiOperation(value = "Find Publisher by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success - Publisher found"),
            @ApiResponse(code = 404, message = "Publisher not found error code ")
    })
    PublisherDTO findById(Long Id);

    @ApiOperation(value = "List all registered publishers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success - Return all registered publishers"),
    })
    List<PublisherDTO> findAll();
}
