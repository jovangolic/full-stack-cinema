package com.jovan.backend_c2.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentErrorResponse {

	private String message;
	private int errorCode;
	private LocalDateTime timeStamp;
	
	public PaymentErrorResponse(String message, int errorCode) {
		this.message = message;
		this.errorCode = errorCode;
		this.timeStamp = LocalDateTime.now();
	}
	
	public PaymentErrorResponse(String message) {
		this.message = message;
	}
	
}