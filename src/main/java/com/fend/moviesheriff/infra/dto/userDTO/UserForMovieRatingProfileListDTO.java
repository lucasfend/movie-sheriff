package com.fend.moviesheriff.infra.dto.userDTO;

import lombok.Builder;

@Builder
public record UserForMovieRatingProfileListDTO(
        String username,
        double rating
) {
}
