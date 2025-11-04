package com.fend.moviesheriff.infra.dto;

import lombok.Builder;

@Builder
public record UserForMovieRatingProfileListDTO(
        String username,
        double rating
) {
}
