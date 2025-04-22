package com.jovan.backend_c2.controller;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jovan.backend_c2.exception.ResourceNotFoundException;
import com.jovan.backend_c2.model.Hall;
import com.jovan.backend_c2.model.Movie;
import com.jovan.backend_c2.model.Projection;
import com.jovan.backend_c2.model.ProjectionType;
import com.jovan.backend_c2.request.ProjectionRequest;
import com.jovan.backend_c2.response.MovieProjectionResponse;
import com.jovan.backend_c2.response.ProjectionAndProjectionTypeResponse;
import com.jovan.backend_c2.response.ProjectionHallResponse;
import com.jovan.backend_c2.response.ProjectionResponse;
import com.jovan.backend_c2.service.IProjectionService;

import lombok.RequiredArgsConstructor;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/projections")
@RequiredArgsConstructor
public class ProjectionController {
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectionController.class);
	private final IProjectionService projectionService;
	
	 @PreAuthorize("hasRole('ROLE_ADMIN')")
	    @PostMapping(value = "/add/new-projection", consumes = "multipart/form-data")
	    public ResponseEntity<ProjectionResponse> addNewProjection(
	             @RequestParam("movieId") Long movieId, 
	             @RequestParam("projectionType") String projectionType,
	             @RequestParam("hallId") Long hallId, 
	             @RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
	             @RequestParam("ticketPrice") Double ticketPrice,
	             @RequestParam("photo")MultipartFile photo ) throws SQLException, IOException {
	        
	        logger.info("Received request to add new projection with movieId: {}, projectionType: {}, hallId: {}, dateTime: {}, ticketPrice: {}",
	                movieId, projectionType, hallId, dateTime, ticketPrice);
	        byte[] photoBytes = photo.getBytes();
	        String photoStr = Base64.getEncoder().encodeToString(photoBytes);
	        Projection savedProjection = projectionService.addNewProjection(movieId, projectionType, hallId, dateTime, ticketPrice,photoStr);
	        Movie newMovie = savedProjection.getMovie();
	        ProjectionType type = savedProjection.getProjectionType();
	        Hall newHall = savedProjection.getHall();
	        MovieProjectionResponse movieProjectionResponse = new MovieProjectionResponse(newMovie.getId(), newMovie.getName(), newMovie.getDuration());
	        ProjectionAndProjectionTypeResponse typeResponse = new ProjectionAndProjectionTypeResponse(type.getId(), type.getName());
	        ProjectionHallResponse projectionHallResponse = new ProjectionHallResponse(newHall.getId(), newHall.getName());
	        ProjectionResponse projectionResponse = new ProjectionResponse(savedProjection.getId(), movieProjectionResponse, typeResponse,
	                projectionHallResponse, savedProjection.getDateTime(), savedProjection.getTicketPrice(), photoStr);
	        return ResponseEntity.ok(projectionResponse);
	    }
	
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/update/{projectionId}")
	public ResponseEntity<ProjectionResponse> updateProjection(@PathVariable Long projectionId,@RequestBody ProjectionRequest request) {
		Projection projection = projectionService.updateProjecton(projectionId, request.getMovieId(), request.getProjectionType(), request.getHallId(),
				request.getDateTime(), request.ticketPrice);
		ProjectionResponse response = getProjectionResponse(projection);
		return ResponseEntity.ok(response);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete/projection/{projectionId}")
	public ResponseEntity<Void> deleteProjection(@PathVariable Long projectionId){
		projectionService.delete(projectionId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@GetMapping("/projection/{projectionId}")
	public ResponseEntity<Optional<ProjectionResponse>> getProjectionById(@PathVariable Long projectionId){
		Optional<Projection> projection = projectionService.getProjectionById(projectionId);
		return projection.map(p -> {
			ProjectionResponse response = getProjectionResponse(p);
			return ResponseEntity.ok(Optional.of(response));
		}).orElseThrow(
				() -> new ResourceNotFoundException("Projection not found"));
	}
	
	
	/*@GetMapping("/all-projections")
	public ResponseEntity<List<ProjectionResponse>> getAllProjections() throws SQLException{
		List<Projection> projections = projectionService.getAllProjections();
		List<ProjectionResponse> responses = new ArrayList<>();
		for(Projection p : projections) {
			ProjectionResponse response = getProjectionResponse(p);
			responses.add(response);
		}
		return ResponseEntity.ok(responses);
	}*/
	@GetMapping("/all-projections")
	public ResponseEntity<List<ProjectionResponse>> getAllProjections() {
		List<Projection> projections = projectionService.getAllProjections();

        List<ProjectionResponse> response = projections.stream().map(projection -> {
            Movie movie = projection.getMovie();
            byte[] imageBytes = null;
            try {
                if (movie.getPhoto() != null) {
                    imageBytes = movie.getPhoto().getBytes(1, (int) movie.getPhoto().length());
                }
            } catch (Exception e) {
            	logger.error("Error fetching projections: ", e.getMessage());
                //return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }

            String imageBase64 = imageBytes != null ? Base64.getEncoder().encodeToString(imageBytes) : null;

            MovieProjectionResponse movieProjectionResponse = new MovieProjectionResponse(
                    movie.getId(),
                    movie.getName(),
                    movie.getDuration(),
                    movie.getDistributor(),
                    movie.getCountry(),
                    movie.getYear(),
                    movie.getDescription(),
                    imageBase64
            );

            return new ProjectionResponse(
                    projection.getId(),
                    movieProjectionResponse,
                    new ProjectionAndProjectionTypeResponse(projection.getProjectionType().getId(), projection.getProjectionType().getName()),
                    new ProjectionHallResponse(projection.getHall().getId(), projection.getHall().getName()),
                    projection.getDateTime(),
                    projection.getTicketPrice(),
                    imageBase64
            );

        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    
	
	@GetMapping("/projection/projectionType")
	public List<String> getProjectionTypes(){
		return projectionService.getProjectionsByProjectionType();
	}
	
	@GetMapping("/search-projection")
	public ResponseEntity<List<ProjectionResponse>> searchProjection(
			@RequestParam("dateFrom") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDateTime dateFrom,
			@RequestParam("dateTo") @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDateTime dateTo,
			@RequestParam("priceFrom") Double priceFrom,
			@RequestParam("priceTo") Double priceTo,
			@RequestParam("projectionTypeName") String projectioTypeName,
			@RequestParam("movieId") Long movieId,
			@RequestParam("hallName") String hallName) {
		List<Projection> projections = projectionService.search(dateFrom, dateTo, priceFrom, priceTo, projectioTypeName, movieId, hallName);
		List<ProjectionResponse> responses = new ArrayList<>();
		for(Projection p : projections) {
			ProjectionResponse response = getProjectionResponse(p);
			responses.add(response);
		}
		if(responses.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.ok(responses);
		}
	}
	
	
	@GetMapping("/{id}/{movieId}")
	public ResponseEntity<List<ProjectionResponse>> getProjectionsByMovieId(@PathVariable Long movieId) {
		List<Projection> projections = getAllProjectionByMovieId(movieId);
		List<ProjectionResponse> responses = projections.stream()
				.map(this::getProjectionResponse)
				.collect(Collectors.toList());
		return ResponseEntity.ok(responses);
	}
	
	
	@GetMapping("/projections-by-date")
	public ResponseEntity<List<ProjectionResponse>> getProjectionsByDate(
			@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
	        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
		List<Projection> projections = projectionService.getProjectionsByDate(startDate, endDate);
		List<ProjectionResponse> responses = new ArrayList<>();
		for(Projection p : projections) {
			ProjectionResponse response = getProjectionResponse(p);
			responses.add(response);
		}
		if(responses.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.ok(responses);
		}
	}
	
	public ProjectionResponse getProjectionResponse(Projection projection) {
	    Movie movie = projection.getMovie();
	    ProjectionType type = projection.getProjectionType();
	    Hall hall = projection.getHall();

	    MovieProjectionResponse movieProjectionResponse = new MovieProjectionResponse(
	            movie.getId(), movie.getName(), movie.getDuration());

	    ProjectionAndProjectionTypeResponse typeResponse = new ProjectionAndProjectionTypeResponse(
	            type.getId(), type.getName());

	    ProjectionHallResponse projectionHallResponse = new ProjectionHallResponse(
	            hall.getId(), hall.getName());

	    String photoStr = null;
	    try {
	        Blob photoBlob = movie.getPhoto();
	        if (photoBlob != null) {
	            byte[] photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
	            photoStr = Base64.getEncoder().encodeToString(photoBytes);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace(); // Po želji loguj grešku
	    }

	    return new ProjectionResponse(
	            projection.getId(),
	            movieProjectionResponse,
	            typeResponse,
	            projectionHallResponse,
	            projection.getDateTime(),
	            projection.getTicketPrice(),
	            photoStr
	    );
	}
	
	
	private List<Projection> getAllProjectionByMovieId(Long movieId){
		return projectionService.findByMovieId(movieId);
	}
}


