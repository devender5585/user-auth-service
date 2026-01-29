package com.example.userauth.service;

import com.example.userauth.dto.LoginRequest;
import com.example.userauth.dto.UserRegistrationRequest;
import com.example.userauth.dto.UserResponse;

public interface UserService {

	UserResponse registerUser(UserRegistrationRequest request);
	
	UserResponse login(LoginRequest request);

}
