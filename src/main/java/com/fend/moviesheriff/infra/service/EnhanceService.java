package com.fend.moviesheriff.infra.service;

import com.fend.moviesheriff.domain.dto.movieratingDTOs.MovieRatingResponseDTO;
import com.fend.moviesheriff.domain.model.MovieRating;
import com.fend.moviesheriff.domain.repository.MovieRatingRepository;
import com.fend.moviesheriff.infra.dto.allRatingsDTO.ResponseAllMovieRatingsDTO;
import com.fend.moviesheriff.infra.dto.controllerDTO.SearchFromTmdbDTO;
import com.fend.moviesheriff.infra.dto.externalDTO.TmdbGetExternalRequestDTO;
import com.fend.moviesheriff.infra.dto.userDTO.UserForMovieRatingProfileListDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.fend.moviesheriff.infra.utils.AverageRatingUtil.averageRating;

@Service
@RequiredArgsConstructor
public class EnhanceService {
    private final TmdbService tmdbService;
    private final MovieRatingRepository movieRatingRepository;

    public MovieRatingResponseDTO getEnhancedAllDTO(String id) {
        TmdbGetExternalRequestDTO movieNotEnhanced = tmdbService.getMovieDetailsOnExternalAPIById(id);
        List<MovieRating> listOfMovieRatings = movieRatingRepository.findAllByExternalIdWithUser(id);

        double average = averageRating(listOfMovieRatings);

        List<UserForMovieRatingProfileListDTO> userDTOS = listOfMovieRatings.stream()
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

    public List<ResponseAllMovieRatingsDTO> getEnhancedAllDTOByName(String original_title) {
        SearchFromTmdbDTO resultsFromExternalAPI = tmdbService.getMovieListOnExternalAPIByName(original_title);

        List<ResponseAllMovieRatingsDTO> responseAllMovieRatingsDTOList;

        return responseAllMovieRatingsDTOList = resultsFromExternalAPI.results().stream()
                .map(tmdbGetExternalRequestDTO -> {
                    String externalId = tmdbGetExternalRequestDTO.id();

                    List<MovieRating> listOfMovieRatings = movieRatingRepository.findAllByExternalId(externalId);

                    if (Objects.isNull(listOfMovieRatings) || listOfMovieRatings.isEmpty()) {
                        return null;
                    }

                    double averaged = averageRating(listOfMovieRatings);

                    return ResponseAllMovieRatingsDTO.builder()
                            .externalId(externalId)
                            .original_title(tmdbGetExternalRequestDTO.original_title())
                            .poster_path(tmdbGetExternalRequestDTO.poster_path())
                            .overview(tmdbGetExternalRequestDTO.overview())
                            .rating(averaged)
                            .build();

                })
                .filter(Objects::nonNull)
                .toList();
    }
}
