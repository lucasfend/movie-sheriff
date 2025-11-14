package com.fend.moviesheriff.domain.dto.movieratingDTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateMovieRatingDTO(
        @NotNull(message = "External Id cannot be null")
        @NotEmpty(message = "External Id cannot be empty")
        String externalId,

        @NotNull(message = "Rating cannot be null")
        @Min(0)
        double rating,

        @NotNull(message = "Username cannot be null")
        String username
) {
}
