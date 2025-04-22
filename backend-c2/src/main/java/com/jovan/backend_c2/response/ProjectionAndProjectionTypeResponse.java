package com.jovan.backend_c2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProjectionAndProjectionTypeResponse {

	/*
	 *This is DTO for specific projection. 
	 */
	private Long id;
	
	private String name;
	
}
