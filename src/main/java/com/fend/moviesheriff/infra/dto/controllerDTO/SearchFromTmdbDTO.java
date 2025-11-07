package com.fend.moviesheriff.infra.dto.controllerDTO;

import com.fend.moviesheriff.infra.dto.externalDTO.TmdbGetExternalRequestDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record SearchFromTmdbDTO(
        List<TmdbGetExternalRequestDTO> results
) {
}
