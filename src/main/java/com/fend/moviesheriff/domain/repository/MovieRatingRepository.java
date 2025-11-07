package com.fend.moviesheriff.domain.repository;

import com.fend.moviesheriff.domain.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    @Query("SELECT r FROM MovieRating r JOIN FETCH r.user WHERE r.externalId = :externalId")
    List<MovieRating> findAllByExternalIdWithUser(@Param("externalId") String externalId);
    List<MovieRating> findAllByUser_Id(Long id);
    List<MovieRating> findAllByExternalId(String externalId);
}
