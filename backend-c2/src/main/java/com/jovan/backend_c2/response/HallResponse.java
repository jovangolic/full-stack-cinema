package com.jovan.backend_c2.response;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HallResponse {

	
	private Long id;
	
	private String name;
	
	//private Set<ProjectionTypeResponse> projectionTypeResponses = new HashSet<>();
	private Set<HallAndProjectionTypeResponse> hallAndProjectionTypeResponses = new HashSet<>();
	
	private Set<SeatResponse> seatResponses = new HashSet<>();
	
	private Set<ProjectionResponse> projectionResponses = new HashSet<>();
	
	public HallResponse(Long id, String name) {
		this.id = id;
		this.name = name;
	}
}