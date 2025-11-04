package com.fend.moviesheriff.domain.repository;

import com.fend.moviesheriff.domain.dto.CreateMovieRatingDTO;
import com.fend.moviesheriff.domain.model.MovieRating;
import com.fend.moviesheriff.domain.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for MovieRatingRepository")
class MovieRatingRepositoryTest {

    @Autowired
    private MovieRatingRepository movieRatingRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("save method persists movie rating when successful")
    void saveUser_PersistMovieRatingOnDatabase_WhenSuccessful() {
        CreateMovieRatingDTO createMovieRatingDTO = createMovieRating();

        MovieRating savedMovieRating = this.movieRatingRepository.save(toMovieRating(createMovieRatingDTO));

        Assertions.assertThat(savedMovieRating).isNotNull();

        Assertions.assertThat(savedMovieRating.getId()).isNotNull();

        Assertions.assertThat(savedMovieRating.getExternalId()).isNotNull();
        Assertions.assertThat(savedMovieRating.getRating()).isNotNull();
        Assertions.assertThat(savedMovieRating.getUser()).isNotNull();

        Assertions.assertThat(savedMovieRating.getExternalId()).isEqualTo(createMovieRatingDTO.externalId());
        Assertions.assertThat(savedMovieRating.getRating()).isEqualTo(createMovieRatingDTO.rating());
        Assertions.assertThat(savedMovieRating.getUser().getId()).isEqualTo(createMovieRatingDTO.userID());
    }

    @Test
    @DisplayName("save method updates movie rating when successful")
    void saveUser_UpdateMovieRatingOnDatabase_WhenSuccessful() {
        CreateMovieRatingDTO createMovieRatingDTO = createMovieRating();
        MovieRating savedMovieRating = this.movieRatingRepository.save(toMovieRating(createMovieRatingDTO));

        savedMovieRating.setRating(2.0);
        MovieRating updatedMovieRating = this.movieRatingRepository.save(savedMovieRating);

        Assertions.assertThat(updatedMovieRating).isNotNull();
        Assertions.assertThat(updatedMovieRating.getId()).isNotNull();
    }

    @Test
    @DisplayName("delete method removes movie rating when successful")
    void deleteUser_DeleteMovieRatingOnDatabase_WhenSuccessful() {
        CreateMovieRatingDTO createMovieRatingDTO = createMovieRating();
        MovieRating savedMovieRating = this.movieRatingRepository.save(toMovieRating(createMovieRatingDTO));

        this.movieRatingRepository.delete(savedMovieRating);

        Optional<MovieRating> movieRating = this.movieRatingRepository.findById(savedMovieRating.getId());
        Assertions.assertThat(movieRating).isNotPresent();
    }

    @Test
    @DisplayName("findById method returns empty MovieRating when movie rating not found")
    void findById_ReturnsEmptyMovieRating_WhenMovieRatingNotFound() {
        Optional<MovieRating> foundMovieRating = this.movieRatingRepository.findById(2L);
        Assertions.assertThat(foundMovieRating).isEmpty();
    }

    private CreateMovieRatingDTO createMovieRating() {
        return CreateMovieRatingDTO.builder()
                .externalId("24")
                .rating(4.5)
                .userID(1L)
                .build();
    }

    private MovieRating toMovieRating(CreateMovieRatingDTO createMovieRatingDTO) {
        User user = new User();
        User savedUser = userRepository.save(user);

        MovieRating movieRating = new MovieRating();
        movieRating.setExternalId(createMovieRatingDTO.externalId());
        movieRating.setRating(createMovieRatingDTO.rating());
        movieRating.setUser(savedUser);

        return movieRating;
    }

}