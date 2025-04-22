package com.jovan.backend_c2.exception;



public class InternalServerException extends RuntimeException {
	
	public InternalServerException(String message) {
        super(message);
    }

}