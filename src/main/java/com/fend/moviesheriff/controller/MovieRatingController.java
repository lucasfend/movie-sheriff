package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.domain.dto.movieratingDTOs.CreateMovieRatingDTO;
import com.fend.moviesheriff.domain.service.persistence.MovieRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("movies/ratings")
@RequiredArgsConstructor
public class MovieRatingController {
    private final MovieRatingService movieRatingService;

    @PostMapping
    public ResponseEntity<Void> saveMovieRating(@RequestBody @Valid CreateMovieRatingDTO createMovieRatingDTO) {
        movieRatingService.saveMovieRating(createMovieRatingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
