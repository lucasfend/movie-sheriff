package com.fend.moviesheriff.infra.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TmdbService {
    private final WebClient webClient;

    public TmdbGetRequestDTO getMovieDetailsOnExternalAPIById(String externalMovieId) {
        return webClient.get()
                .uri("/movie/" + externalMovieId)
                .retrieve()
                .bodyToMono(TmdbGetRequestDTO.class)
                .block();
    }

    public SearchFromTmdbDTO getMovieListByName(String original_title) {
        SearchFromTmdbDTO response = webClient.get()
                .uri("/search/movie?query=" + original_title)
                .retrieve()
                .bodyToMono(SearchFromTmdbDTO.class)
                .block();

        List<TmdbGetRequestDTO> tmdbGetRequestDTOList;
        if (response != null && response.results() != null) {
            tmdbGetRequestDTOList = response.results();
        } else {
            tmdbGetRequestDTOList = List.of();
        }

        return SearchFromTmdbDTO.builder()
                .results(tmdbGetRequestDTOList)
                .build();
    }
}
