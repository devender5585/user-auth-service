package com.example.userauth.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long expirationTime;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String email, String role) {
		return Jwts.builder()
				.setSubject(email)
				.claim("role", role)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(key)
				.compact();
	}

	public String extractEmail(String token) {
	    return parseClaims(token).getBody().getSubject();
	}

	public String extractRole(String token) {
	    return parseClaims(token).getBody().get("role", String.class);
	}
	
	public boolean validateToken(String token) {
	    try {
	        parseClaims(token);
	        return true;
	    } catch (JwtException | IllegalArgumentException ex) {
	        return false;
	    }
	}
	
	private Jws<Claims> parseClaims(String token) {
	    return Jwts.parserBuilder()
	            .setSigningKey(key)
	            .build()
	            .parseClaimsJws(token);
	}

}
