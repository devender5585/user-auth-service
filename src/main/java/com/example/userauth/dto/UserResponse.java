package com.example.userauth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserResponse {

	private Long id;
    private String name;
    private String email;
    private String role;
    private String status;
    private String accessToken;
    private String refreshToken;
    

}
