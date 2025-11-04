package com.fend.moviesheriff.domain.dto;

import com.fend.moviesheriff.infra.dto.MovieRatingForUserProfileListDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.List;

@Builder
public record UserResponseDTO(
        @NotNull(message = "username cannot be null")
        @NotEmpty(message = "username cannot be empty")
        String username,

        List<MovieRatingForUserProfileListDTO> movie_ratings
) {
}

