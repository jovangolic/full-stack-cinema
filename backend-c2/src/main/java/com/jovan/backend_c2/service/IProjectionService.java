package com.jovan.backend_c2.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.jovan.backend_c2.model.Projection;

public interface IProjectionService {
	
	Projection addNewProjection(Long movieId, String projectionType,Long hallId,LocalDateTime dateTime, Double ticketPrice, String photo);
	
	Optional<Projection> getProjectionById(Long projectionId);
	
	List<Projection> getAllProjections();
	
	Projection updateProjecton(Long projectionId,Long movieId, String projectionType,Long hallId,LocalDateTime dateTime, Double ticketPrice);
	
	void delete(Long projectionId);
	
	List<Projection> findByMovieId(Long movieId);
	
	List<Projection> search(LocalDateTime dateFrom, LocalDateTime dateTo, Double priceFrom, Double priceTo, 
			String projectionTypeName, Long movieId, String hallName);
	
	List<Projection> getProjectionsByDate(LocalDateTime dateFrom, LocalDateTime dateTo);
	
	List<String> getProjectionsByProjectionType();
	
	List<Projection> getAvailableProjections(LocalDateTime startDate, LocalDateTime endDate);
	
}
