package com.fend.moviesheriff.infra.dto.externalDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

@Builder
public record TmdbGetExternalRequestDTO(
        String id,
        String original_language,
        String original_title,
        String overview,
        String poster_path,
        String backdrop_path,
        @JsonFormat(pattern = "MM-dd-yyyy")
        String release_date
) {
}
