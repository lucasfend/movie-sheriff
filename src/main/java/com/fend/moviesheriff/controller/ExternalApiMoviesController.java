package com.fend.moviesheriff.controller;

import com.fend.moviesheriff.infra.service.SearchFromTmdbDTO;
import com.fend.moviesheriff.infra.service.TmdbGetRequestDTO;
import com.fend.moviesheriff.infra.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies/external")
@RequiredArgsConstructor
public class ExternalApiMoviesController {
    private final TmdbService tmdbService;

    @GetMapping("{id}")
    public ResponseEntity<TmdbGetRequestDTO> getMovieDetailsOnExternalAPIById(@PathVariable String id) {
        return ResponseEntity.ok(tmdbService.getMovieDetailsOnExternalAPIById(id));
    }

    @GetMapping("name/{movie_name}")
    public ResponseEntity<SearchFromTmdbDTO> getMovieListByName(@PathVariable String movie_name) {
        return ResponseEntity.ok(tmdbService.getMovieListByName(movie_name));
    }
}
