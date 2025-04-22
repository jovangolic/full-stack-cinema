package com.jovan.backend_c2.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jovan.backend_c2.model.MoviesComingSoon;



@Repository
public interface MovieComingSoonRepository extends JpaRepository<MoviesComingSoon, Long> {

	
	List<MoviesComingSoon> findByReleaseDateAfter(LocalDate date);
}

