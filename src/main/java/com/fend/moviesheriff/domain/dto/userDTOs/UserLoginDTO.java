package com.fend.moviesheriff.domain.dto.userDTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserLoginDTO(
        @NotNull String username,

        @NotBlank String password
) {
}
