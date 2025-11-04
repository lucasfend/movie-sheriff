package com.fend.moviesheriff.infra.service;

import com.fend.moviesheriff.domain.model.MovieRating;
import com.fend.moviesheriff.domain.repository.MovieRatingRepository;
import com.fend.moviesheriff.domain.dto.MovieRatingResponseDTO;
import com.fend.moviesheriff.infra.dto.TmdbGetExternalRequestDTO;
import com.fend.moviesheriff.infra.dto.UserForMovieRatingProfileListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnhanceService {
    private final TmdbService tmdbService;
    private final MovieRatingRepository movieRatingRepository;

    public MovieRatingResponseDTO getEnhancedAllDTO(String id) {
        TmdbGetExternalRequestDTO movieNotEnhanced = tmdbService.getMovieDetailsOnExternalAPIById(id);
        List<MovieRating> listOfRatings = movieRatingRepository.findAllByExternalIdWithUser(id);

        double average = averageRating(listOfRatings);

        List<UserForMovieRatingProfileListDTO> userDTOS = listOfRatings.stream()
                .map(movieRating -> {
                    return UserForMovieRatingProfileListDTO.builder()
                            .username(movieRating.getUser().getUsername())
                            .rating(movieRating.getRating())
                            .build();
                })
                .distinct()
                .toList();

        return MovieRatingResponseDTO.builder()
                .original_title(movieNotEnhanced.original_title())
                .poster_path(movieNotEnhanced.poster_path())
                .average_rating(average)
                .user_list(userDTOS)
                .build();
    }


    // aux methods
    public double averageRating(List<MovieRating> movieRatings) {
        double rating = movieRatings.stream()
                .mapToDouble(MovieRating::getRating)
                .average()
                .orElse(0.0);

        return Math.round(rating * 10.0) / 10.0;
    }
}
