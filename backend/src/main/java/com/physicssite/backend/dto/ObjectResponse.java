package com.physicssite.backend.dto;

public class ObjectResponse<T> extends ServerResponse {
	
	private T value;
	
	public ObjectResponse(String error) {
		super(error, true);
		value = null;
	}
	
	public ObjectResponse(T value) {
		super("Success!");
		this.value = value;
	}
	
	public T getValue() {
		return value;
	}
	
}
