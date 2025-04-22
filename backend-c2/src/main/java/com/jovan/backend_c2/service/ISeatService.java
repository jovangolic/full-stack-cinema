package com.jovan.backend_c2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.jovan.backend_c2.model.Seat;
import com.jovan.backend_c2.response.SeatResponse;

public interface ISeatService {

	Seat createSeat(Long seatId, Integer seatNumber, Long hallId) throws IOException, SQLException;
	Optional<Seat> getSeatById(Long id);
	List<Seat> getAvailableSeatsByHall(boolean available, Long hallId);
	List<Seat> getAllSeats();
	List<SeatResponse> getSeatsByProjectionId(Long projectionId);
	List<Seat> getSeatsByHallId(Long hallId);
	void createSeatsForHall(Long hallId, int numberOfSeats); 
}
