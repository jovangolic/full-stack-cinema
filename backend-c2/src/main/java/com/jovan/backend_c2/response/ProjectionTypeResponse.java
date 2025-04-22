package com.jovan.backend_c2.response;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data
public class ProjectionTypeResponse {

private Long id;
	
	private String name;
	
	private Set<HallResponse> halls = new HashSet<>();
	
	private Set<ProjectionResponse> projections = new HashSet<>();
	
	public ProjectionTypeResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}
