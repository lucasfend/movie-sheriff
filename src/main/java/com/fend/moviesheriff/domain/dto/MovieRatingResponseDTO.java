package com.fend.moviesheriff.domain.dto;

import com.fend.moviesheriff.infra.dto.UserForMovieRatingProfileListDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record MovieRatingResponseDTO(
        String original_title,
        String poster_path,
        double average_rating,
        List<UserForMovieRatingProfileListDTO> user_list
) {
}
