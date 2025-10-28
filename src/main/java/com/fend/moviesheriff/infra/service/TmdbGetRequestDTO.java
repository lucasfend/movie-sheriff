package com.fend.moviesheriff.infra.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

@Builder
public record TmdbGetRequestDTO(
        String id,
        String original_language,
        String original_title,
        String overview,
        String poster_path,
        @JsonFormat(pattern = "MM-dd-yyyy")
        String release_date
) {
}
