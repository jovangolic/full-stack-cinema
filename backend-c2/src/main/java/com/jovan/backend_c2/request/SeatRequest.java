package com.jovan.backend_c2.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatRequest {

	private Integer seatNumber;
    private boolean available;
    private Long hallId;
}
