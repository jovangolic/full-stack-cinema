package com.jovan.backend_c2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.jovan.backend_c2.model.Hall;
import com.jovan.backend_c2.model.Seat;
import com.jovan.backend_c2.model.Projection;
import com.jovan.backend_c2.repository.HallRepository;
import com.jovan.backend_c2.repository.ProjectionRepository;
import com.jovan.backend_c2.repository.SeatRepository;
import com.jovan.backend_c2.response.SeatHallResponse;
import com.jovan.backend_c2.response.SeatResponse;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Service
public class SeatService implements ISeatService {
	
	
	private final SeatRepository seatRepository;
	private final HallRepository hallRepository;
	private final ProjectionRepository projectionRepository;

	@Override
	public Seat createSeat(Long seatId, Integer seatNumber, Long hallId)
			throws IOException, SQLException {
		Hall hall = hallRepository.findById(hallId).orElseThrow(
				() -> new IllegalArgumentException("Hall not found with id : " + hallId));
		Seat seat = new Seat();
		seat.setId(seatId);
		seat.setSeatNumber(seatNumber);
		seat.setHall(hall);
		return seatRepository.save(seat);
	}

	@Override
	public Optional<Seat> getSeatById(Long id) {
		return Optional.of(seatRepository.findById(id).get());
	}

	@Override
	public List<Seat> getAvailableSeatsByHall(boolean available, Long hallId) {
		Hall hall = hallRepository.findById(hallId).orElseThrow(
				() -> new IllegalArgumentException("Hall not found with id : " + hallId));
		return seatRepository.findByAvailableAndHall(true, hall);
	}

	@Override
	public List<Seat> getAllSeats() {
		return seatRepository.findAll();
	}

	@Override
	public List<SeatResponse> getSeatsByProjectionId(Long projectionId) {
	    Projection projection = projectionRepository.findById(projectionId)
	        .orElseThrow(() -> new IllegalArgumentException("Projection not found"));
	    
	    Hall hall = projection.getHall();

	    List<Seat> seats = seatRepository.findByHall(hall);
	    
	    return seats.stream()
	        .map(seat -> new SeatResponse(
	            seat.getId(),
	            seat.getSeatNumber(),
	            seat.isAvailable(),
	            new SeatHallResponse(seat.getHall().getId(), seat.getHall().getName(),seat.isAvailable()
	        )))
	        .collect(Collectors.toList());
	}

	@Override
	public List<Seat> getSeatsByHallId(Long hallId) {
		return seatRepository.findByHallId(hallId);
	}

	@Override
	public void createSeatsForHall(Long hallId, int numberOfSeats) {
		Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new EntityNotFoundException("Hall not found"));
  
		  for (int i = 1; i <= numberOfSeats; i++) {
		      Seat seat = new Seat();
		      seat.setSeatNumber(i);
		      seat.setAvailable(true);
		      seat.setHall(hall);
		      seatRepository.save(seat);
		  }
	}

}

