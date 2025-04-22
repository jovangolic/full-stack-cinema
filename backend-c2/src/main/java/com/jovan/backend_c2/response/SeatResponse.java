package com.jovan.backend_c2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatResponse {

	private Long id;
	
	private Integer seatNumber;
	
	private boolean available = true;
	
	private SeatHallResponse seatHallResponse;
	
	public SeatResponse(Long id, Integer seatNumber) {
		this.id = id;
		this.seatNumber = seatNumber;
	}
	
}
