package com.leonardo.bookstoremanager.controller;

import com.leonardo.bookstoremanager.dto.JwtRequest;
import com.leonardo.bookstoremanager.dto.JwtResponse;
import com.leonardo.bookstoremanager.dto.MessageDTO;
import com.leonardo.bookstoremanager.dto.UserDTO;
import com.leonardo.bookstoremanager.service.AuthenticationService;
import com.leonardo.bookstoremanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDocs{

    private AuthenticationService authenticationService;

    UserService userService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO){
        return userService.create(userToCreateDTO);
    }

    @PutMapping("/{id}")
    public MessageDTO update(
            @PathVariable Long id,
            @RequestBody @Valid  UserDTO userToUpdateDTO) {
        return userService.update(id, userToUpdateDTO);
    }

    @GetMapping("{id}")
    public UserDTO findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @GetMapping()
    public List<UserDTO> findAll() {
        return userService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping(value = "/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
        return authenticationService.createAuthenticationToken(jwtRequest);
    }
}
