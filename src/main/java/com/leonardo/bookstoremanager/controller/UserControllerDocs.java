package com.leonardo.bookstoremanager.controller;

import com.leonardo.bookstoremanager.dto.MessageDTO;
import com.leonardo.bookstoremanager.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

@Api("system users management")
public interface UserControllerDocs {

    @ApiOperation(value = "User creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201,message = "Success user creation"),
            @ApiResponse(code = 400, message = "Missing required field or an error on validation field rules")
    })
    MessageDTO create(UserDTO userToCreateDTO);

    @ApiOperation(value = "User update operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201,message = "Success user updated"),
            @ApiResponse(code = 400, message = "Missing required field or an error on validation field rules")
    })
     MessageDTO update(Long id,UserDTO userToUpdateDTO);

    @ApiOperation(value = "Find User by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success - User found"),
            @ApiResponse(code = 404, message = "User not found error code ")
    })
    UserDTO findById(Long id);

    @ApiOperation(value = "List all Users registered")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return all registered users")
    })
    List<UserDTO> findAll();

    @ApiOperation(value = "Delete User by id operation")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success User deleted"),
            @ApiResponse(code = 404, message = "User not found error code ")
    })
    void delete(Long id);
}
