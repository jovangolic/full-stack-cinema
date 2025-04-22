package com.jovan.backend_c2.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jovan.backend_c2.model.Movie;



@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

	@Query("SELECT m FROM Movie m WHERE m.duration BETWEEN :durationFrom AND :durationTo")
	List<Movie> findByDuration(@Param("durationFrom") Integer durationFrom,@Param("durationTo")  Integer durationTo);
	
}
