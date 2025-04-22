package com.jovan.backend_c2.exception;



public class UserInvalidException extends RuntimeException {

    public UserInvalidException(String message) {
        super(message);
    }
}
