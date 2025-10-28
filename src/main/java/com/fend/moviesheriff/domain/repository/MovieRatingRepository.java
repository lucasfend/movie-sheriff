package com.fend.moviesheriff.domain.repository;

import com.fend.moviesheriff.domain.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
}
