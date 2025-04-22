package com.jovan.backend_c2.request;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentRequest {

	private Long userId;
	private Double amount;
	private String currency;
}
