package com.example.userauth.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.userauth.dto.LoginRequest;
import com.example.userauth.dto.UserRegistrationRequest;
import com.example.userauth.dto.UserResponse;
import com.example.userauth.entity.RefreshToken;
import com.example.userauth.entity.User;
import com.example.userauth.exception.EmailAlreadyExistsException;
import com.example.userauth.exception.InvalidCredentialsException;
import com.example.userauth.exception.InvalidRefreshTokenException;
import com.example.userauth.repository.RefreshTokenRepository;
import com.example.userauth.repository.UserRepository;
import com.example.userauth.security.JwtUtil;
import com.example.userauth.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Value("${jwt.refresh.expiration}")
	private long refreshTokenExpiration;

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil ;
	private final RefreshTokenRepository refreshTokenRepository;

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil,
			RefreshTokenRepository refreshTokenRepository) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
		this.refreshTokenRepository = refreshTokenRepository;
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
	
	@Transactional
	@Override
	public UserResponse login(LoginRequest request) {

	    User user = userRepository.findByEmail(request.getEmail())
	            .orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));

	    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	        throw new InvalidCredentialsException("Invalid email or password");
	    }

	    String accessToken = jwtUtil.generateToken(user.getEmail(),user.getRole());
	    RefreshToken refreshToken = createRefreshToken(user);
	    
	    UserResponse response = new UserResponse();
	    response.setId(user.getId());
	    response.setName(user.getName());
	    response.setEmail(user.getEmail());
	    response.setRole(user.getRole());
	    response.setStatus(user.getStatus());
	    response.setAccessToken(accessToken);

	    response.setRefreshToken(refreshToken.getToken());;

	    return response;
	}
	
	private RefreshToken createRefreshToken(User user) {

	    refreshTokenRepository.deleteByUserId(user.getId());

	    RefreshToken refreshToken = new RefreshToken();
	    refreshToken.setUser(user);
	    refreshToken.setToken(UUID.randomUUID().toString());
	    refreshToken.setExpiryTime(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000));
	    return refreshTokenRepository.save(refreshToken);
	}

	public UserResponse refreshAccessToken(String oldRefreshToken) {

	    RefreshToken refreshToken = refreshTokenRepository
	            .findByToken(oldRefreshToken)
	            .orElseThrow(() -> new InvalidRefreshTokenException("Invalid refresh token"));

	    if (refreshToken.getExpiryTime().isBefore(LocalDateTime.now())) {
	        throw new RuntimeException("Refresh token expired");
	    }

	    User user = refreshToken.getUser();

	    refreshTokenRepository.deleteByToken(oldRefreshToken);
	    
	    RefreshToken newRefreshToken = new RefreshToken();
	    newRefreshToken.setUser(user);
	    newRefreshToken.setToken(UUID.randomUUID().toString());
	    newRefreshToken.setExpiryTime(LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000));

	    refreshTokenRepository.save(newRefreshToken);

	    // 🔴 Issue NEW access token
	    UserResponse response = new UserResponse();
	    response.setAccessToken(
	            jwtUtil.generateToken(user.getEmail(), user.getRole())
	    );
	    response.setRefreshToken(newRefreshToken.getToken());

	    return response;
	}

	@Transactional
	@Override
	public void logout(String refreshToken) {
	    refreshTokenRepository.deleteByToken(refreshToken);
	}
	
	

}
