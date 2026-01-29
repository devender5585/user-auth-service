package com.example.userauth.service.impl;

import org.springframework.stereotype.Service;

import com.example.userauth.dto.UserRegistrationRequest;
import com.example.userauth.entity.User;
import com.example.userauth.repository.UserRepository;
import com.example.userauth.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User registerUser(UserRegistrationRequest request) {
		
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new RuntimeException("Email already registered");
		}

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword()); // encoder later
		user.setRole("USER");
		user.setStatus("ACTIVE");

		return userRepository.save(user);
	}

}
