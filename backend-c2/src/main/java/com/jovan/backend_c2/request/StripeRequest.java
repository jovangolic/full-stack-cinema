package com.jovan.backend_c2.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StripeRequest {

	private Double amount;
	private String currency;
}
