package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.domain.dto.movieratingDTOs.MovieRatingResponseDTO;
import com.fend.moviesheriff.infra.dto.allRatingsDTO.ResponseAllMovieRatingsDTO;
import com.fend.moviesheriff.infra.dto.controllerDTO.SearchFromTmdbDTO;
import com.fend.moviesheriff.infra.dto.externalDTO.TmdbGetExternalRequestDTO;
import com.fend.moviesheriff.infra.service.EnhanceService;
import com.fend.moviesheriff.infra.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
@RequiredArgsConstructor
public class ExternalApiMoviesController {
    private final TmdbService tmdbService;
    private final EnhanceService enhanceService;

    @GetMapping("profile/{id}")
    public ResponseEntity<MovieRatingResponseDTO> getEnhancedAllDTO(@PathVariable String id) {
        return ResponseEntity.ok(enhanceService.getEnhancedAllDTO(id));
    }

    @GetMapping("rated/{movie_name}")
    public ResponseEntity<List<ResponseAllMovieRatingsDTO>> getEnhancedAllDTOByName(@PathVariable String movie_name) {
        return ResponseEntity.ok(enhanceService.getEnhancedAllDTOByName(movie_name));
    }

    @GetMapping("external/{movie_name}")
    public ResponseEntity<SearchFromTmdbDTO> getMovieListOnExternalAPIByName(@PathVariable String movie_name) {
        return ResponseEntity.ok(tmdbService.getMovieListOnExternalAPIByName(movie_name));
    }
}
