package com.jovan.backend_c2.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jovan.backend_c2.model.Seat;
import com.jovan.backend_c2.model.Hall;
import com.jovan.backend_c2.repository.HallRepository;
import com.jovan.backend_c2.repository.SeatRepository;
import com.jovan.backend_c2.request.SeatRequest;
import com.jovan.backend_c2.response.SeatHallResponse;
import com.jovan.backend_c2.response.SeatResponse;
import com.jovan.backend_c2.service.ISeatService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/seats")
@RequiredArgsConstructor
public class SeatController {
	
	
	private final ISeatService seatService;
	private final HallRepository hallRepository;
	private final SeatRepository seatRepository;
	
	
	@PostMapping("/add/new-seat")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<SeatResponse> createSeat(@RequestBody SeatRequest request) {
	    Hall hall = hallRepository.findById(request.getHallId())
	            .orElseThrow(() -> new RuntimeException("Hall not found"));

	    Seat seat = new Seat();
	    seat.setSeatNumber(request.getSeatNumber());
	    seat.setAvailable(request.isAvailable());
	    seat.setHall(hall);

	    seatRepository.save(seat);

	    SeatHallResponse hallResponse = new SeatHallResponse(hall.getId(), hall.getName(),seat.isAvailable());
	    SeatResponse response = new SeatResponse(seat.getId(), seat.getSeatNumber(),seat.isAvailable(), hallResponse);
	    return ResponseEntity.ok(response);
	}
	
	
	/*@PostMapping("/add/new-seat")
	public ResponseEntity<SeatResponse> createSeat(@RequestBody SeatResponse seatResponse) throws IOException, SQLException{
		Seat seat = seatService.createSeat(seatResponse.getId(),seatResponse.getSeatNumber(), seatResponse.getSeatHallResponse().getId());
		SeatResponse response = new SeatResponse(seat.getId(), seat.getSeatNumber(), new SeatHallResponse(seat.getHall().getId(), seat.getHall().getName()));
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}*/
	
	@PostMapping("/add/seats-for-hall/{hallId}/{count}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<String> createSeatsForHall(@PathVariable Long hallId, @PathVariable int count) {
	    seatService.createSeatsForHall(hallId, count);
	    return ResponseEntity.ok("Successfully created " + count + " seats for hall with ID: " + hallId);
	}
	
	
	@GetMapping("/all-seats")
	public ResponseEntity<List<SeatResponse>> getAllSeats(){
		List<Seat> seats = seatService.getAllSeats();
		List<SeatResponse> responses = seats.stream().map(this::getSeatResponse).collect(Collectors.toList());
		return ResponseEntity.ok(responses);
	}
	
	@GetMapping("/available/{hallId}")
	public ResponseEntity<List<SeatResponse>> getAvailableSeats(@PathVariable Long hallId){
		List<Seat> availableSeats = seatService.getAvailableSeatsByHall(true, hallId);
		List<SeatResponse> responses = availableSeats.stream().map(this::getSeatResponse).collect(Collectors.toList());
		return ResponseEntity.ok(responses);
	}
	
	
	@GetMapping("/by-hall/{hallId}")
	public ResponseEntity<List<SeatResponse>> getSeatsByHall(@PathVariable Long hallId) {
	    List<Seat> seats = seatService.getSeatsByHallId(hallId);
	    List<SeatResponse> response = seats.stream()
	        .map(this::getSeatResponse) 
	        .collect(Collectors.toList());

	    return ResponseEntity.ok(response);
	}
	
	
	/*private SeatResponse getSeatResponse(Seat seat) {
		return new SeatResponse(seat.getId(), seat.getSeatNumber());
	}*/
	
	@GetMapping("/by-projection/{projectionId}")
	public ResponseEntity<List<SeatResponse>> getSeatsByProjectionId(@PathVariable Long projectionId){
		List<SeatResponse> response = seatService.getSeatsByProjectionId(projectionId);
		return ResponseEntity.ok(response);
	}
	
	private SeatResponse getSeatResponse(Seat seat) {
	    SeatHallResponse hallResponse = new SeatHallResponse(
	        seat.getHall().getId(),
	        seat.getHall().getName(),
	        seat.isAvailable()
	    );
	    return new SeatResponse(
	        seat.getId(),
	        seat.getSeatNumber(),
	        seat.isAvailable(),
	        hallResponse
	    );
	}
}
