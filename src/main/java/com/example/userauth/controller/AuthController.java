package com.example.userauth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userauth.dto.LoginRequest;
import com.example.userauth.dto.RefreshTokenRequest;
import com.example.userauth.dto.UserRegistrationRequest;
import com.example.userauth.dto.UserResponse;
import com.example.userauth.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public UserResponse register(@Valid @RequestBody UserRegistrationRequest request) {
        return userService.registerUser(request);
    }
    
    @PostMapping("/login")
    public UserResponse login(@Valid @RequestBody LoginRequest request) {
        return userService.login(request);
    }
	
    @PostMapping("/refresh")
    public UserResponse refreshToken(
           @Valid @RequestBody RefreshTokenRequest request) {
        return userService.refreshAccessToken(request.getRefreshToken());
    }
}

