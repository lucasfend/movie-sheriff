package com.fend.moviesheriff.infra.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MovieRatingForUserProfileListDTO(
        String externalId,
        String original_title,
        String poster_path,

        @NotNull(message = "Rating cannot be null")
        @Min(0)
        double rating
) {
}