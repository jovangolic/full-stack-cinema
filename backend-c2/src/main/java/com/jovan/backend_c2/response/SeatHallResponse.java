package com.jovan.backend_c2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatHallResponse {

	/*
	 *This is DTO for specific Hall
	 */
	
	private Long id;
	
	private String name;
	
	private boolean available;
}


