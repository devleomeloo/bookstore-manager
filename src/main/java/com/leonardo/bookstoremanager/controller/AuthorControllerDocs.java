package com.leonardo.bookstoremanager.controller;

import com.leonardo.bookstoremanager.dto.AuthorDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Authors Management")
public interface AuthorControllerDocs {

    @ApiOperation(value = "Author creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success - Author created"),
            @ApiResponse(code = 400, message = "Missing required fields, wrong field range value or author already registered on system")
    })
    AuthorDTO create(AuthorDTO authorDTO);

    @ApiOperation(value = "Find Author by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success - Author found"),
            @ApiResponse(code = 404, message = "Author not found error code ")
    })
    AuthorDTO findById(Long Id);
}