package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.domain.dto.movieratingDTOs.CreateMovieRatingDTO;
import com.fend.moviesheriff.domain.mapper.MovieRatingMapper;
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
    private final MovieRatingMapper movieRatingMapper;

    @PostMapping
    public ResponseEntity<CreateMovieRatingDTO> saveMovieRating(@RequestBody @Valid CreateMovieRatingDTO createMovieRatingDTO) {
        return new ResponseEntity<>(movieRatingService.saveMovieRating(createMovieRatingDTO), HttpStatus.CREATED);
    }

}
