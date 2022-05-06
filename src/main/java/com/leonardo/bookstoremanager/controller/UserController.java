package com.leonardo.bookstoremanager.controller;

import com.leonardo.bookstoremanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDocs{

    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
