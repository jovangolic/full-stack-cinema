package com.jovan.backend_c2.service;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.jovan.backend_c2.model.MoviesComingSoon;

import io.jsonwebtoken.io.IOException;

public interface IMovieComingSoonService {
	
	
	MoviesComingSoon addNewMovie(String name, Integer duration, String distributor, 
			String country, Integer year, String description, LocalDate releaseDate, Blob photo) throws IOException, SQLException;
	
	void deleteMovie(Long movieId);
	
	List<MoviesComingSoon> findUpcomingMovies();
	
	List<MoviesComingSoon> allUpcomingMovies();
	
	byte[] getMoviePhotoByMovieId(Long movieId) throws SQLException;
	
	Optional<MoviesComingSoon> getMovieDetails(Long movieComingSoonId) throws SQLException;
}

