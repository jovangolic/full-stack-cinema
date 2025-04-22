package com.jovan.backend_c2.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {

	private Long projectionId;
	private Integer seatNumber;
    private Double price;
    private Long userId;
    private String email;
    private Integer quantity;
}
