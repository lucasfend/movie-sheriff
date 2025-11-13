package com.fend.moviesheriff.domain.service.persistence;

import com.fend.moviesheriff.domain.dto.movieratingDTOs.CreateMovieRatingDTO;
import com.fend.moviesheriff.domain.mapper.MovieRatingMapper;
import com.fend.moviesheriff.domain.model.MovieRating;
import com.fend.moviesheriff.domain.model.User;
import com.fend.moviesheriff.domain.repository.MovieRatingRepository;
import com.fend.moviesheriff.exceptions.httpstatus.BadRequestException;
import com.fend.moviesheriff.exceptions.httpstatus.SQLConstraintViolationException;
import com.fend.moviesheriff.infra.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieRatingService {
    private final UserService userService;
    private final MovieRatingRepository movieRatingRepository;
    private final TmdbService tmdbService;
    private final MovieRatingMapper movieRatingMapper;

    public CreateMovieRatingDTO saveMovieRating(CreateMovieRatingDTO createMovieRatingDTO) {
        User user = userService.findByIdOrThrowException(createMovieRatingDTO.userID());
        tmdbService.getMovieDetailsOnExternalAPIById(createMovieRatingDTO.externalId());

        MovieRating movieRatingToSave = new MovieRating();
        movieRatingToSave.setExternalId(createMovieRatingDTO.externalId());
        movieRatingToSave.setRating(createMovieRatingDTO.rating());
        movieRatingToSave.setUser(user);

        user.addMovieRatingToList(movieRatingToSave);

        try {
            movieRatingRepository.save(movieRatingToSave);
        } catch (DataIntegrityViolationException e) {
            throw new SQLConstraintViolationException(e.getMessage());
        }

        return movieRatingMapper.toMovieRatingDTO(movieRatingRepository.save(movieRatingToSave));
    }

    public List<CreateMovieRatingDTO> getAllMovieRating() {
        List<MovieRating> movieRating = movieRatingRepository.findAll();

        return movieRating.stream()
                .map(movieRatingMapper::toMovieRatingDTO)
                .collect(Collectors.toList());
    }

    // internal service operation methods

    public MovieRating findMovieRatingByIdOrThrowException(Long id) {
        return movieRatingRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Movie rating not found"));
    }

    public void deleteMovieRating(Long id) {
        movieRatingRepository.delete(findMovieRatingByIdOrThrowException(id));
    }
}
