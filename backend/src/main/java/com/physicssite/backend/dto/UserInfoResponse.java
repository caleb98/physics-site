package com.physicssite.backend.dto;

import java.util.List;

import com.physicssite.backend.user.User;

public class UserInfoResponse extends ServerResponse {

	private String username;
	private String email;
	private List<String> auths;
	
	public UserInfoResponse(User user) {
		super("Success.");
		username = user.getUsername();
		email = user.getEmail();
		auths = user.getAuthorities();
	}
	
	public UserInfoResponse(String error) {
		super(error, true);
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public List<String> getAuths() {
		return auths;
	}

}
