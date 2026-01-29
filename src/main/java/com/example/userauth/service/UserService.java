package com.example.userauth.service;

import com.example.userauth.dto.UserRegistrationRequest;
import com.example.userauth.dto.UserResponse;

public interface UserService {

	UserResponse registerUser(UserRegistrationRequest request);
}
