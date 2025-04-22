package com.jovan.backend_c2.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jovan.backend_c2.enumeration.TicketStatus;
import com.jovan.backend_c2.model.Projection;
import com.jovan.backend_c2.model.Ticket;



@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

	@Query("SELECT t FROM Ticket t WHERE t.saleDateTime = :dates AND t.projection = :projection AND t.status = :status")
    List<Ticket> findAvailableTicketsBySaleDateTimeAndProjectionAndStatus(
            @Param("dates") LocalDateTime dates,
            @Param("projection") Projection projection,
            @Param("status") TicketStatus status);
	
	Optional<Ticket> findById(Long ticketId);
	
	//Optional<Ticket> findByTicketConfirmationCode(String confirmationCode);
	
	List<Ticket> findByUserEmail(String email);
	
	List<Ticket> findByStatus(TicketStatus status);
	
	List<Ticket> findByProjectionAndStatus(Projection projection, TicketStatus status);
	
	Double countByProjectionId(Long projectionId);
	
	
	List<Ticket> findByTicketConfirmationCode(String confirmationCode);
	List<Ticket> findByProjectionId(Long projectionId);
}
