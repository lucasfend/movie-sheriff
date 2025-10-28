package com.fend.moviesheriff.domain.service;

import com.fend.moviesheriff.dto.MovieRatingDTO;
import com.fend.moviesheriff.domain.model.MovieRating;
import com.fend.moviesheriff.domain.model.User;
import com.fend.moviesheriff.domain.repository.MovieRatingRepository;
import com.fend.moviesheriff.infra.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieRatingService {
    private final UserService userService;
    private final MovieRatingRepository movieRatingRepository;
    private final TmdbService tmdbService;

    public MovieRating saveMovieRating(MovieRatingDTO movieRatingDTO) {
        User user = userService.findByIdOrThrowException(movieRatingDTO.userID());

        try {
            tmdbService.getMovieDetailsOnExternalAPIById(movieRatingDTO.externalId());
        } catch (Exception e) { // colocar excecao customizada depois
            throw new IllegalArgumentException(e.getMessage());
        }

        MovieRating movieRatingToSave = new MovieRating();
        movieRatingToSave.setExternalId(movieRatingDTO.externalId());
        movieRatingToSave.setRating(movieRatingDTO.rating());
        movieRatingToSave.setUser(user);

        return movieRatingRepository.save(movieRatingToSave);
    }

    public MovieRating findMovieRatingByIdOrThrowException(Long id) {
        return movieRatingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Movie with id " + id + " not found"));
    }

    public void deleteMovieRating(Long id) {
        movieRatingRepository.delete(findMovieRatingByIdOrThrowException(id));
    }


}
