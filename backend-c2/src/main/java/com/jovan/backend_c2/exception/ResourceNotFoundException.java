package com.jovan.backend_c2.exception;


public class ResourceNotFoundException extends RuntimeException {

	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
