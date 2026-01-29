package com.example.userauth.controller;

import com.example.userauth.dto.LoginRequest;
import com.example.userauth.dto.UserRegistrationRequest;
import com.example.userauth.dto.UserResponse;
import com.example.userauth.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRegistrationRequest request) {
    	System.out.println("dsd");
        return userService.registerUser(request);
    }
    
    
    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }

}
