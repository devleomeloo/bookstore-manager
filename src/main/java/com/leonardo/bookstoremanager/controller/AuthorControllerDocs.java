package com.leonardo.bookstoremanager.controller;

import com.leonardo.bookstoremanager.dto.AuthorDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

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
    AuthorDTO findById(Long id);

    @ApiOperation(value = "List all Author registered")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all registered authors")
    })
    List<AuthorDTO> findAll();


    @ApiOperation(value = "Delete Author by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success Author deleted"),
            @ApiResponse(code = 404, message = "Author not found error code ")
    })
    void delete(Long id);
}
