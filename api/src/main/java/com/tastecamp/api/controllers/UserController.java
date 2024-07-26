package com.tastecamp.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.tastecamp.api.dtos.UserDTO;
import com.tastecamp.api.models.UserModel;
import com.tastecamp.api.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
public class UserController {

    final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid UserDTO body) {
        UserModel user = userService.createUser(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    
}
