package com.physicssite.backend.dto;

public class ServerResponse {

	private String message;
	private boolean isError;
	
	public ServerResponse(String message) {
		this.message = message;
		isError = false;
	}
	
	public ServerResponse(String message, boolean isError) {
		this.message = message;
		this.isError = isError;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean getIsError() {
		return isError;
	}
	
	public void setError(boolean isError) {
		this.isError = isError;
	}
	
}
