package com.fend.moviesheriff.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "users",  uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id", "email", "username"})
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user", orphanRemoval = true)
    private List<MovieRating> movieRatings;

    // aux methods

    public void addMovieRatingToList(MovieRating movieRating) {
        movieRatings.add(movieRating);
        movieRating.setUser(this);
    }

    public void removeMovieRatingFromList(MovieRating movieRating) {
        movieRatings.remove(movieRating);
        movieRating.setUser(null);
    }
}

