package com.jovan.backend_c2.model;

import java.time.LocalDateTime;

import com.jovan.backend_c2.enumeration.TicketStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@Data
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="projection_id")
	private Projection projection;
	
	@ManyToOne
	@JoinColumn(name="seat_id")
	private Seat seat;
	
	private LocalDateTime saleDateTime;
	
	private Double price;
	
	@Enumerated(EnumType.STRING)
	private TicketStatus status;
	
	@Column(name="confirmation_code")
	private String ticketConfirmationCode;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
}