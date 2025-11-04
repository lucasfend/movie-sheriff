package com.fend.moviesheriff.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
@Entity
@Table(name = "movie_rating",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "external_id"})
        })
public class MovieRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_id")
    private String externalId;

    @Max(value = 5)
    @Min(value = 0)
    private double rating;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
