package com.jovan.backend_c2.service;

import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jovan.backend_c2.exception.ResourceNotFoundException;
import com.jovan.backend_c2.model.MoviesComingSoon;
import com.jovan.backend_c2.repository.MovieComingSoonRepository;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieComingSoonService implements IMovieComingSoonService {
	
	private final MovieComingSoonRepository comingSoonRepository;

	@Override
	public List<MoviesComingSoon> findUpcomingMovies() {
		return comingSoonRepository.findByReleaseDateAfter(LocalDate.now());
	}

	@Override
	public MoviesComingSoon addNewMovie(String name, Integer duration, String distributor, String country, Integer year,
			String description, LocalDate releaseDate, Blob photo) throws IOException, SQLException {
		MoviesComingSoon comingSoon = new MoviesComingSoon();
		comingSoon.setName(name);
		comingSoon.setDuration(duration);
		comingSoon.setDistributor(distributor);
		comingSoon.setCountry(country);
		comingSoon.setYear(year);
		comingSoon.setDescription(description);
		comingSoon.setReleaseDate(releaseDate);
		if(photo != null) {
			comingSoon.setPhoto(photo);
		}
		return comingSoonRepository.save(comingSoon);
	}

	@Override
	public void deleteMovie(Long movieId) {
		Optional<MoviesComingSoon> theMovie = comingSoonRepository.findById(movieId);
		if(theMovie.isPresent()) {
			comingSoonRepository.deleteById(movieId);
		}
	}

	@Override
	public byte[] getMoviePhotoByMovieId(Long movieId) throws SQLException {
		Optional<MoviesComingSoon> theMovie = comingSoonRepository.findById(movieId);
		if(theMovie.isEmpty()) {
			throw new ResourceNotFoundException("Sorry, Movie not found");
		}
		Blob photoBlob = theMovie.get().getPhoto();
		if(photoBlob != null) {
			return photoBlob.getBytes(1, (int)photoBlob.length());
		}
		return null;
	}
	

	@Override
	public List<MoviesComingSoon> allUpcomingMovies() {
		return comingSoonRepository.findAll();
	}

	@Override
	public Optional<MoviesComingSoon> getMovieDetails(Long movieComingSoonId) throws SQLException {
		return comingSoonRepository.findById(movieComingSoonId);
	}

}
