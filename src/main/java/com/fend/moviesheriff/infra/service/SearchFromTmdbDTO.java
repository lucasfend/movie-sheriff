package com.fend.moviesheriff.infra.service;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchFromTmdbDTO(
        List<TmdbGetRequestDTO> results
) {
}
