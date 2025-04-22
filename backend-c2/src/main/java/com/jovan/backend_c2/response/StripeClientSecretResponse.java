package com.jovan.backend_c2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class StripeClientSecretResponse {
	private String clientSecret;
}

