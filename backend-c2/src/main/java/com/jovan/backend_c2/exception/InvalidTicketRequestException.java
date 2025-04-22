package com.jovan.backend_c2.exception;



public class InvalidTicketRequestException extends RuntimeException {
	public InvalidTicketRequestException(String message) {
		super(message);
	}
}
