package com.jovan.backend_c2.response;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectionResponse {

	
	/*private Long id;
    private MovieProjectionResponse movieProjectionResponse;
    private ProjectionAndProjectionTypeResponse projectionAndProjectionTypeResponse;
    private ProjectionHallResponse projectionHallResponse;
    private LocalDateTime dateTime;
    private Double ticketPrice;
    private Set<TicketResponse> ticketResponses = new HashSet<>();
    
    // Dodato polje za sliku
    private byte[] movieImage; // Polje za sliku u formatu byte[]

    // Postojeći konstruktori
    public ProjectionResponse(Long id, MovieProjectionResponse movieProjectionResponse, LocalDateTime dateTime, Double ticketPrice) {
        this.id = id;
        this.movieProjectionResponse = movieProjectionResponse;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
    }

    public ProjectionResponse(Long id, MovieProjectionResponse movieProjectionResponse, 
                              ProjectionAndProjectionTypeResponse projectionAndProjectionTypeResponse,
                              ProjectionHallResponse projectionHallResponse, 
                              LocalDateTime dateTime, Double ticketPrice, byte[] movieImage) {
        this.id = id;
        this.movieProjectionResponse = movieProjectionResponse;
        this.projectionAndProjectionTypeResponse = projectionAndProjectionTypeResponse;
        this.projectionHallResponse = projectionHallResponse;
        this.dateTime = dateTime;
        this.ticketPrice = ticketPrice;
        this.movieImage = movieImage; // Dodajemo sliku u konstruktor
    }*/
	 private Long id;
	    private MovieProjectionResponse movieProjectionResponse;
	    private ProjectionAndProjectionTypeResponse projectionAndProjectionTypeResponse;
	    private ProjectionHallResponse projectionHallResponse;
	    private LocalDateTime dateTime;
	    private Double ticketPrice;
	    private Set<TicketResponse> ticketResponses = new HashSet<>();

	    // Dodajemo polje za sliku kao String
	    private String movieImage; // Slika kao Base64 string

	    public ProjectionResponse(Long id, MovieProjectionResponse movieProjectionResponse, 
	                              ProjectionAndProjectionTypeResponse projectionAndProjectionTypeResponse,
	                              ProjectionHallResponse projectionHallResponse, 
	                              LocalDateTime dateTime, Double ticketPrice, String movieImage) {
	        this.id = id;
	        this.movieProjectionResponse = movieProjectionResponse;
	        this.projectionAndProjectionTypeResponse = projectionAndProjectionTypeResponse;
	        this.projectionHallResponse = projectionHallResponse;
	        this.dateTime = dateTime;
	        this.ticketPrice = ticketPrice;
	        this.movieImage = movieImage; // Dodajemo sliku u konstruktor
	    }
	
}