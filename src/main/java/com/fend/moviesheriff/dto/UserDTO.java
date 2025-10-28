package com.fend.moviesheriff.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserDTO(
        @NotNull(message = "username cannot be null")
        @NotEmpty(message = "username cannot be empty")
        String username,

        @NotNull(message = "email cannot be null")
        @NotEmpty(message = "email cannot be empty")
        String email,

        @NotNull(message = "password cannot be null")
        @NotEmpty(message = "password cannot be empty")
        String password
) {
}
