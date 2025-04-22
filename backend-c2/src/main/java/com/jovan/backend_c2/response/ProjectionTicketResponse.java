package com.jovan.backend_c2.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectionTicketResponse {

	/*
	 *This is DTO for specific projection. 
	 */
	
	private Long id;
	
	private MovieResponse movieResponse;
	
	private ProjectionTypeResponse typeResponse;
	
	private HallResponse hallResponse;
	
	private LocalDateTime dateTime;
	
	private Double ticketPrice;
}
