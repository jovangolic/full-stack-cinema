package com.jovan.backend_c2.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jovan.backend_c2.dto.UserDTO;
import com.jovan.backend_c2.enumeration.TicketStatus;
import com.jovan.backend_c2.exception.InvalidTicketRequestException;
import com.jovan.backend_c2.exception.ResourceNotFoundException;
import com.jovan.backend_c2.model.Hall;
import com.jovan.backend_c2.model.Movie;
import com.jovan.backend_c2.model.Projection;
import com.jovan.backend_c2.model.ProjectionType;
import com.jovan.backend_c2.model.Seat;
import com.jovan.backend_c2.model.Ticket;
import com.jovan.backend_c2.model.User;
import com.jovan.backend_c2.repository.ProjectionRepository;
import com.jovan.backend_c2.repository.SeatRepository;
import com.jovan.backend_c2.repository.UserRepository;
import com.jovan.backend_c2.request.TicketRequest;
import com.jovan.backend_c2.request.TicketStatusUpdateRequest;
import com.jovan.backend_c2.response.HallResponse;
import com.jovan.backend_c2.response.MovieResponse;
import com.jovan.backend_c2.response.ProjectionTicketResponse;
import com.jovan.backend_c2.response.ProjectionTypeResponse;
import com.jovan.backend_c2.response.SeatHallResponse;
import com.jovan.backend_c2.response.SeatResponse;
import com.jovan.backend_c2.response.TicketResponse;
import com.jovan.backend_c2.service.ITicketService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

	
	private final ITicketService ticketService;
	private final ProjectionRepository projectionRepository;
	private final SeatRepository seatRepository;
	private final UserRepository userRepository;
	
	@PostMapping("/add/new-ticket")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<TicketResponse> addNewTicket(
			@Valid @RequestBody TicketRequest request,
			@RequestParam("status") TicketStatus status) throws SQLException, IOException{
		Ticket savedTicket = ticketService.addNewTicket(request.getProjectionId(),request.getSeatNumber(),request.getPrice(),
				request.getUserId(),request.getEmail(),request.getQuantity(), status);
		TicketResponse response = new TicketResponse();
		return ResponseEntity.ok(response);
	}
	
	@PutMapping("/update/{ticketId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<TicketResponse> updateTicket(@PathVariable Long ticketId,
			@RequestBody TicketRequest request) throws SQLException, IOException{
		Ticket ticket = ticketService.updateTicket(ticketId,request.getProjectionId(),request.getSeatNumber(),request.getPrice(),
				request.getUserId(), request.getEmail(),request.getQuantity());
		TicketResponse response = getTicketResponse(ticket);
		return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	//ovde se koristi @PatchMapping da se azurira samo deo resursa (status karte) a ne cela karta
	@PatchMapping("/{ticketId}/status")
	public ResponseEntity<TicketResponse> updateTicketStatus(@PathVariable Long ticketId,
			@RequestBody TicketStatusUpdateRequest statusUpdateRequest){
		Ticket updateTicket = ticketService.updateTicketStatus(ticketId, statusUpdateRequest.getStatus());
		TicketResponse response = getTicketResponse(updateTicket);
		return ResponseEntity.ok(response);
	}
	
	//metoda za kupovinu rezervisane karte
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	@PostMapping("/purchase-reserved/{ticketId}")
	public ResponseEntity<TicketResponse> purchaseReservedTicket(@PathVariable Long ticketId, @RequestBody TicketRequest ticketRequest){
		Long projectionId = ticketRequest.getProjectionId();
		if (projectionId == null) {
	        throw new IllegalArgumentException("Projection ID is required");
	    }
		Ticket updateTicket= ticketService.purchaseReservedTicket(ticketId);
		TicketResponse response = getTicketResponse(updateTicket);
		return ResponseEntity.ok(response);
	}
	
	//metoda za oktazivanje rezervisane karte
	@PostMapping("/cancel-reserved/{ticketId}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<TicketResponse> cancelReservedTicket(@PathVariable Long ticketId, @RequestBody TicketRequest ticketRequest){
		Long projectionId = ticketRequest.getProjectionId();
		if (projectionId == null) {
	        throw new IllegalArgumentException("Projection ID is required");
	    }
		Ticket cancelTicket = ticketService.cancelReservedTicket(ticketId);
		TicketResponse response = getTicketResponse(cancelTicket);
		return ResponseEntity.ok(response);
	}
	
	//metoda za otkazivanje kupljene karte
	@PostMapping("/cancel-purchased/{ticketId}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<TicketResponse> cancelPurchasedTicket(@PathVariable Long ticketId, @RequestBody TicketRequest ticketRequest){
		Long projectionId = ticketRequest.getProjectionId();
		if (projectionId == null) {
	        throw new IllegalArgumentException("Projection ID is required");
	    }
		Ticket cancelTicket = ticketService.cancelPurchasedTicket(ticketId);
		TicketResponse response = getTicketResponse(cancelTicket);
		return ResponseEntity.ok(response);
	}
	
	
	@GetMapping("/available-tickets")
	public ResponseEntity<List<TicketResponse>> getAvailableTickets(
			@RequestParam("date") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDateTime date,
			@RequestParam("projection") Projection projection,
			@RequestParam("status") TicketStatus status) throws SQLException{
		List<Ticket> availableTickets = ticketService.getAvailableTickets(date, projection ,status);
		List<TicketResponse> responses = new ArrayList<>();
		for(Ticket ticket : availableTickets) {
			TicketResponse response = getTicketResponse(ticket);
			responses.add(response);
		}
		if(responses.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.ok(responses);
		}
	}
	
	//metoda za pretragu karata po njihovom statusu
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/status/{status}")
	public ResponseEntity<List<TicketResponse>> getTicketsByStatus(@PathVariable TicketStatus status){
		List<Ticket> tickets = ticketService.getTicketsByStatus(status);
		List<TicketResponse> responses = tickets.stream().map(
				this::getTicketResponse)
				.collect(Collectors.toList());
		return ResponseEntity.ok(responses);
	}
	
	@DeleteMapping("/delete/ticket/{ticketId}")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> deleteTicket(@PathVariable Long ticketId){
		ticketService.deleteTicket(ticketId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/all-tickets")
	public ResponseEntity<List<TicketResponse>> getAllTickets() throws SQLException{
		List<Ticket> tickets = ticketService.getAllTickets();
		List<TicketResponse> ticketResponses = new ArrayList<>();
		for(Ticket t : tickets) {
			TicketResponse response = getTicketResponse(t);
			ticketResponses.add(response);
		}
		return ResponseEntity.ok(ticketResponses);
	}
	
	@GetMapping("/ticket/{ticketId}")
	public ResponseEntity<Optional<TicketResponse>> getTicketById(@PathVariable Long ticketId){
		Optional<Ticket> ticket = ticketService.getTicketById(ticketId);
		return ticket.map(
				t -> {
					TicketResponse ticketResponse = getTicketResponse(t);
					return ResponseEntity.ok(Optional.of(ticketResponse));
				}).orElseThrow(
						() -> new ResourceNotFoundException("Ticket not found"));
	}
	
	
	/*@PostMapping("/ticket/{projectionId}/buy")
	public ResponseEntity<?> saveTicket(@PathVariable Long projectionId, @RequestBody TicketRequest ticketRequest, @RequestParam("status") TicketStatus status){
	    try {
	        if (status == null) {
	            return ResponseEntity.badRequest().body("Status nije validan.");
	        }

	        System.out.println("Received status: " + status);
	        String confirmCode = RandomStringUtils.randomNumeric(10);
	        String confirmationCode = ticketService.saveTicket(projectionId, ticketRequest, status, confirmCode);
	        return ResponseEntity.ok("Ticket bought successfully. Your confirmation code is : " + confirmationCode);
	    } catch (InvalidTicketRequestException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}*/
	@PostMapping("/ticket/{projectionId}/buy")
	public ResponseEntity<?> saveTicket(
	        @PathVariable Long projectionId,
	        @RequestBody TicketRequest ticketRequest,
	        @RequestParam("status") TicketStatus status,
	        Authentication authentication) {
	    
	    try {
	        if (status == null) {
	            return ResponseEntity.badRequest().body("Status nije validan.");
	        }
	        System.out.println("Received status: " + status);
	        String confirmCode = RandomStringUtils.randomNumeric(10);
	        System.out.println("Authentication: " + authentication);
	        if (authentication == null || !authentication.isAuthenticated()) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not logged in.");
	        }
	        // Dobavljanje email-a prijavljenog korisnika
	        String userEmail = authentication.getName();

	        String confirmationCode = ticketService.saveTicket(
	                projectionId, ticketRequest, status, confirmCode, userEmail);

	        return ResponseEntity.ok("Ticket bought successfully. Your confirmation code is : " + confirmationCode);
	    } catch (InvalidTicketRequestException | IllegalArgumentException e) {
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
	
	
	@GetMapping("/confirmation/{confirmationCode}")
	public ResponseEntity<List<TicketResponse>> getTicketByConfirmationCode(@PathVariable String confirmationCode){
		List<Ticket> tickets = ticketService.getTicketsByComfirmCode(confirmationCode);
		List<TicketResponse> responses = tickets.stream().map(this::getTicketResponse).collect(Collectors.toList());
		return ResponseEntity.ok(responses);
	}
	
	@GetMapping("/available-seats/{projectionId}")
    public ResponseEntity<List<SeatResponse>> getAvailableSeats(@PathVariable Long projectionId) {
        List<Seat> availableSeats = ticketService.getAvailableSeats(projectionId);
        List<SeatResponse> seatResponses = availableSeats.stream()
                .map(seat -> new SeatResponse(
                        seat.getId(),
                        seat.getSeatNumber(),
                        seat.isAvailable(),
                        new SeatHallResponse(seat.getHall().getId(), seat.getHall().getName(),seat.isAvailable())
                ))
                .collect(Collectors.toList());
        return ResponseEntity.ok(seatResponses);
    }
	
	
	@GetMapping("/count/{projectionId}")
	public ResponseEntity<Double> countTicketsByProjection(@PathVariable Long projectionId){
		Double count = ticketService.countTicketsByProjection(projectionId);
		return ResponseEntity.ok(count);
	}
	
	@GetMapping("/user/{email}/bought-tickets")
	public ResponseEntity<List<TicketResponse>> getTicketsByUserEmail(@PathVariable String email){
		List<Ticket> tickets = ticketService.getTicketsByUserEmail(email);
		List<TicketResponse> responses = new ArrayList<>();
		for(Ticket t : tickets) {
			TicketResponse response = getTicketResponse(t);
			responses.add(response);
		}
		return ResponseEntity.ok(responses);
	}
	
	
	@GetMapping("/taken/{projectionId}")
	public ResponseEntity<List<Seat>> getTakenSeats(@PathVariable Long projectionId) {
	    List<Seat> takenSeats = ticketService.getTakenSeatsForProjection(projectionId);
	    return ResponseEntity.ok(takenSeats);
	}
	
	private TicketResponse getTicketResponse(Ticket ticket) {
		Projection projection = ticket.getProjection();
		Seat seat = ticket.getSeat();
		Movie movie = projection.getMovie();
		Hall hall = projection.getHall();
		User user = ticket.getUser();
		ProjectionType type = projection.getProjectionType();
		MovieResponse movieResponse = new MovieResponse(movie.getId(), movie.getName(),movie.getDuration());
		ProjectionTypeResponse typeResponse = new ProjectionTypeResponse(type.getId(),type.getName());
		HallResponse hallResponse = new HallResponse(hall.getId(),hall.getName());
		ProjectionTicketResponse projectionTicketResponse = new ProjectionTicketResponse(projection.getId(), movieResponse, typeResponse,hallResponse,
				projection.getDateTime(), projection.getTicketPrice());
		SeatResponse seatResponse = new SeatResponse(seat.getId(), seat.getSeatNumber());
		UserDTO userDTO = new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(),user.getPassword());
		return new TicketResponse(ticket.getId(),
				projectionTicketResponse,
				seatResponse,
				ticket.getSaleDateTime(),
				ticket.getStatus(), userDTO);
	}
	
}
