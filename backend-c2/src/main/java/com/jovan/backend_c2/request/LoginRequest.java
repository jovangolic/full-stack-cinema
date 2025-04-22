package com.jovan.backend_c2.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

	@NotBlank
	private String email;
	
	@NotBlank
	private String password;
	
}
