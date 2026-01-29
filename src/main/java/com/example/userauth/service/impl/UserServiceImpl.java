package com.example.userauth.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userauth.dto.LoginRequest;
import com.example.userauth.dto.UserRegistrationRequest;
import com.example.userauth.dto.UserResponse;
import com.example.userauth.entity.User;
import com.example.userauth.exception.EmailAlreadyExistsException;
import com.example.userauth.exception.InvalidCredentialsException;
import com.example.userauth.repository.UserRepository;
import com.example.userauth.security.JwtUtil;
import com.example.userauth.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil ;

	public UserServiceImpl(UserRepository userRepository,
			PasswordEncoder passwordEncoder,
			JwtUtil jwtUtil) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	@Override
	public UserResponse registerUser(UserRegistrationRequest request) {
		
		if (userRepository.existsByEmail(request.getEmail())) {
			throw new EmailAlreadyExistsException("Email already registered");
		}

		User user = new User();
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword())); // password encoding
		user.setRole("USER");
		user.setStatus("ACTIVE");
		
		User savedUser = userRepository.save(user);

		UserResponse response = new UserResponse();
		response.setId(savedUser.getId());
		response.setName(savedUser.getName());
		response.setEmail(savedUser.getEmail());
		response.setRole(savedUser.getRole());
		response.setStatus(savedUser.getStatus());

		return response;
	}
	
	@Override
	public UserResponse login(LoginRequest request) {

	    User user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        throw new InvalidCredentialsException("Invalid email or password");
	    }

	    String token = jwtUtil.generateToken(user.getEmail());
	    
	    UserResponse response = new UserResponse();
	    response.setId(user.getId());
	    response.setName(user.getName());
	    response.setEmail(user.getEmail());
	    response.setRole(user.getRole());
	    response.setStatus(user.getStatus());
	    response.setToken(token);

	    return response;
	}



}
