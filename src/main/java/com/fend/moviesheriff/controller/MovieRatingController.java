package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.dto.MovieRatingDTO;
import com.fend.moviesheriff.domain.model.MovieRating;
import com.fend.moviesheriff.domain.service.MovieRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies/ratings")
@RequiredArgsConstructor
public class MovieRatingController {
    private final MovieRatingService movieRatingService;

    @PostMapping
    public ResponseEntity<MovieRating> saveMovieRating(@RequestBody @Valid MovieRatingDTO movieRatingDTO) {
        return new ResponseEntity<>(movieRatingService.saveMovieRating(movieRatingDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<MovieRating> findAllMovieRatings(@PathVariable Long id) {
        return ResponseEntity.ok(movieRatingService.findMovieRatingByIdOrThrowException(id));
    }
}
