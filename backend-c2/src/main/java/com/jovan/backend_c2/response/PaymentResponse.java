package com.jovan.backend_c2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentResponse {

	private Long id;
	
	private Double amount;
	
	private String currency;
	
	private String status; 	
}
