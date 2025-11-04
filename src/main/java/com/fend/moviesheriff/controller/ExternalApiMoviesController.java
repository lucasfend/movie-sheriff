package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.domain.dto.MovieRatingResponseDTO;
import com.fend.moviesheriff.infra.dto.SearchFromTmdbDTO;
import com.fend.moviesheriff.infra.dto.TmdbGetExternalRequestDTO;
import com.fend.moviesheriff.infra.service.EnhanceService;
import com.fend.moviesheriff.infra.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies/external")
@RequiredArgsConstructor
public class ExternalApiMoviesController {
    private final TmdbService tmdbService;
    private final EnhanceService enhanceService;

    @GetMapping("{id}")
    public ResponseEntity<TmdbGetExternalRequestDTO> getMovieDetailsOnExternalAPIById(@PathVariable String id) {
        return ResponseEntity.ok(tmdbService.getMovieDetailsOnExternalAPIById(id));
    }

    @GetMapping("name/{movie_name}")
    public ResponseEntity<SearchFromTmdbDTO> getMovieListByName(@PathVariable String movie_name) {
        return ResponseEntity.ok(tmdbService.getMovieListOnExternalAPIByName(movie_name));
    }

    @GetMapping("enhanced/{id}")
    public ResponseEntity<MovieRatingResponseDTO> getEnhancedAllDTO(@PathVariable String id) {
        return ResponseEntity.ok(enhanceService.getEnhancedAllDTO(id));
    }
}
