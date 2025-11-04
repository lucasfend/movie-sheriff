package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.domain.dto.CreateMovieRatingDTO;
import com.fend.moviesheriff.domain.mapper.MovieRatingMapper;
import com.fend.moviesheriff.domain.model.MovieRating;
import com.fend.moviesheriff.domain.service.MovieRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies/ratings")
@RequiredArgsConstructor
public class MovieRatingController {
    private final MovieRatingService movieRatingService;
    private final MovieRatingMapper movieRatingMapper;

    @PostMapping
    public ResponseEntity<CreateMovieRatingDTO> saveMovieRating(@RequestBody @Valid CreateMovieRatingDTO createMovieRatingDTO) {
        return new ResponseEntity<>(movieRatingService.saveMovieRating(createMovieRatingDTO), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CreateMovieRatingDTO> findMovieRatingByIdOrThrowException(@PathVariable Long id) {
        MovieRating movieRating = movieRatingService.findMovieRatingByIdOrThrowException(id);
        return ResponseEntity.ok(movieRatingMapper.toMovieRatingDTO(movieRating));
    }

    @GetMapping("all")
    public ResponseEntity<List<CreateMovieRatingDTO>> getAllMovieRatings() {
        return ResponseEntity.ok(movieRatingService.getAllMovieRating());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MovieRating> deleteMovieRating(@PathVariable Long id) {
        movieRatingService.deleteMovieRating(id);
        return ResponseEntity.noContent().build();
    }
}
