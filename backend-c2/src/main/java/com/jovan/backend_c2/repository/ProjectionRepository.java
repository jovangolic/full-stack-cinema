package com.jovan.backend_c2.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jovan.backend_c2.model.Projection;



@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Long> {

	
	List<Projection> findByMovieId(Long movieId);
	
	@Query("SELECT p FROM Projection p WHERE "
			+ "(p.dateTime BETWEEN :dateFrom AND :dateTo) AND "
			+ "(p.ticketPrice BETWEEN :priceFrom AND :priceTo) AND "
			+ "(:typeName IS NULL OR p.projectionType.name LIKE :typeName) AND "
			+ "(:movieId IS NULL OR p.movie.id = :movieId) AND "
			+ "(:hallName IS NULL OR p.hall.name LIKE :hallName)")
	List<Projection> search(@Param("dateFrom") LocalDateTime dateFrom, 
	                        @Param("dateTo") LocalDateTime dateTo,
	                        @Param("priceFrom") Double priceFrom, 
	                        @Param("priceTo") Double priceTo,
	                        @Param("typeName") String typeName, 
	                        @Param("movieId") Long movieId,
	                        @Param("hallName") String hallName);

	@Query("SELECT p FROM Projection p WHERE p.dateTime BETWEEN :dateFrom AND :dateTo")
	List<Projection> findProjectionsByDate(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);

	@Query("SELECT DISTINCT p.name FROM ProjectionType p")
    List<String> findDistinctProjectionTypes();
	
	@Query("SELECT p FROM Projection p WHERE p.dateTime BETWEEN :startDate AND :endDate")
    List<Projection> findAvailableProjectionsByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
	
	@Query("SELECT p FROM Projection p JOIN FETCH p.hall JOIN FETCH p.movie JOIN FETCH p.projectionType WHERE p.movie.id = :movieId")
	List<Projection> findProjectionsByMovieId(@Param("movieId") Long movieId);
}