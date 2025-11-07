package com.fend.moviesheriff.infra.dto.allRatingsDTO;

import lombok.Builder;

@Builder
public record ResponseAllMovieRatingsDTO(
        String externalId,
        String original_title,
        String poster_path,
        String overview,
        double rating
) {
}
