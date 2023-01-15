package com.blogbackend.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long userId;
	private String username;
	private String email;
	private List<String> roles;

	public JwtResponse(String accessToken,
					   Long userId,
					   String username,
					   String email,
					   List<String> roles) {
		this.token = accessToken;
		this.userId = userId;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}

	public JwtResponse(String username) {
		this.username = username;
	}
}
