package com.fend.moviesheriff.domain.mapper;

import com.fend.moviesheriff.dto.MovieRatingDTO;
import com.fend.moviesheriff.domain.model.MovieRating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieRatingMapper {
    MovieRating toMovieRating(MovieRatingDTO movieRatingDTO);
}
