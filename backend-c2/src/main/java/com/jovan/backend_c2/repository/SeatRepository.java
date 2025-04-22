package com.jovan.backend_c2.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jovan.backend_c2.model.Hall;
import com.jovan.backend_c2.model.Seat;



@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

	Optional<Seat> findBySeatNumberAndHall(Integer seatNumber, Hall hall);
	List<Seat> findByAvailableAndHall(boolean available, Hall hall);
	List<Seat> findByHall(Hall hall);
	List<Seat> findByHallId(Long hallId);
	
}

