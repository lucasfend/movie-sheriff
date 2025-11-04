package com.fend.moviesheriff.infra.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchFromTmdbDTO(
        List<TmdbGetExternalRequestDTO> results
) {
}
