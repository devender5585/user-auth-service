package com.example.userauth.service;

import com.example.userauth.dto.UserRegistrationRequest;
import com.example.userauth.entity.User;

public interface UserService {

	User registerUser(UserRegistrationRequest request);
}
