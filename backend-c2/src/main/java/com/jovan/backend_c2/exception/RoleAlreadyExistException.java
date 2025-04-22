package com.jovan.backend_c2.exception;



public class RoleAlreadyExistException extends RuntimeException {
	public RoleAlreadyExistException(String message) {
        super(message);
    }
}
