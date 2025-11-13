package com.fend.moviesheriff.infra.utils;

import com.fend.moviesheriff.domain.model.MovieRating;

import java.util.List;

public class AverageRatingUtil {
    public static double averageRating(List<MovieRating> movieRatings) {
        double rating = movieRatings.stream()
                .mapToDouble(MovieRating::getRating)
                .average()
                .orElse(0.0);

        return Math.round(rating * 10.0) / 10.0;
    }
}
