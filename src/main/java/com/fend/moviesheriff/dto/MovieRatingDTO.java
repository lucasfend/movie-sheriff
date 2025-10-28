package com.fend.moviesheriff.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MovieRatingDTO(
        @NotNull(message = "External Id cannot be null")
        @NotEmpty(message = "External Id cannot be empty")
        String externalId,

        @NotNull(message = "Rating cannot be null")
        @NotEmpty(message = "Rating cannot be empty")
        double rating,

        @NotNull(message = "User Id cannot be null")
        @NotEmpty(message = "User Id cannot be empty")
        Long userID
) {
}
