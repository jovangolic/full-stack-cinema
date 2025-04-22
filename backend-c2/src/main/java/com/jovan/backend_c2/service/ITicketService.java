package com.jovan.backend_c2.service;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.jovan.backend_c2.enumeration.TicketStatus;
import com.jovan.backend_c2.model.Projection;
import com.jovan.backend_c2.model.Seat;
import com.jovan.backend_c2.model.Ticket;
import com.jovan.backend_c2.request.TicketRequest;

public interface ITicketService {

	Ticket addNewTicket(Long projectionId,Integer seatNumber,Double price,Long userId,String email,Integer quantity, TicketStatus status) throws SQLException, IOException;
	
	Optional<Ticket> getTicketById(Long tickeId);
	
	List<Ticket> getAllTickets();
	
	void deleteTicket(Long ticketId);
	
	List<Ticket> getAvailableTickets(LocalDateTime dates, Projection projection, TicketStatus status);
	
	Ticket updateTicket(Long ticketId,Long projectionId,Integer seatNumber,Double price,Long userId,String email,Integer quantity);
	
	//Ticket findByTicketConfirmationCode(String confirmationCode);
	
	//String saveTicket(Long projectionId, TicketRequest ticketRequest, TicketStatus status, String confirmationCode);
	
	List<Ticket> getTicketsByUserEmail(String email);
	
	Ticket updateTicketStatus(Long ticketId, TicketStatus status);
    
    Ticket purchaseReservedTicket(Long ticketId);
    
    Ticket cancelReservedTicket(Long ticketId);
    
    Ticket cancelPurchasedTicket(Long ticketId);
    
    List<Ticket> getTicketsByStatus(TicketStatus status);
    
    List<Seat> getAvailableSeats(Long projectionId);
    
    Double countTicketsByProjection(Long projectionId);
    
    List<Ticket> getTicketsByComfirmCode(String confirmCode);

	String saveTicket(Long projectionId, TicketRequest ticketRequest, TicketStatus status, String confirmationCode,
			String userEmail);
	
	List<Seat> getTakenSeatsForProjection(Long projectionId);
	
	
	
}