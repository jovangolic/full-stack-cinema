package com.jovan.backend_c2.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectionHallResponse {

	/*
	 *This is DTO for specific projection. 
	 */
	private Long id;
	
	private String name;
	
	//private Set<ProjectionTypeResponse> projectionTypeResponses = new HashSet<>();
	
	public ProjectionHallResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
