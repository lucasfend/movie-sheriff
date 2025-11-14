package com.fend.moviesheriff.domain.mapper;

import com.fend.moviesheriff.domain.dto.movieratingDTOs.CreateMovieRatingDTO;
import com.fend.moviesheriff.domain.model.MovieRating;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MovieRatingMapper {
    MovieRating toMovieRating(CreateMovieRatingDTO createMovieRatingDTO);

    CreateMovieRatingDTO toMovieRatingDTO(MovieRating movieRating);
}
