package com.fend.moviesheriff.infra.service;

import com.fend.moviesheriff.exceptions.httpstatus.BadRequestException;
import com.fend.moviesheriff.infra.dto.SearchFromTmdbDTO;
import com.fend.moviesheriff.infra.dto.TmdbGetExternalRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TmdbService {
    private final WebClient webClient;

    public TmdbGetExternalRequestDTO getMovieDetailsOnExternalAPIById(String externalMovieId) {
        return webClient.get()
                .uri("/movie/" + externalMovieId)
                .retrieve()
                .onStatus(status -> status.value() == 404,
                        response -> Mono.error(new BadRequestException("Movie id not found on external API")))
                .bodyToMono(TmdbGetExternalRequestDTO.class)
                .block();
    }

    public SearchFromTmdbDTO getMovieListOnExternalAPIByName(String original_title) {
        List<TmdbGetExternalRequestDTO> listOfResults;
        SearchFromTmdbDTO resultFromSearchOnExternalDb = webClient.get()
                .uri("/search/movie?query=" + original_title)
                .retrieve()
                .bodyToMono(SearchFromTmdbDTO.class)
                .block();

        if (resultFromSearchOnExternalDb == null || resultFromSearchOnExternalDb.results().isEmpty()) {
            throw new BadRequestException("Movie name not found on external API");
        } else {
            listOfResults = resultFromSearchOnExternalDb.results();
        }

        return SearchFromTmdbDTO.builder()
                .results(listOfResults)
                .build();
    }
}
