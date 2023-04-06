package com.example.myinsta.dto;

import com.example.myinsta.annotations.ValidUsername;
import com.example.myinsta.annotations.ValidEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO(

        @JsonProperty(required = true)
        @NotNull
        Long id,

        @JsonProperty(required = true)
        @NotNull
        @NotBlank
        @Size(min = 6, max = 20)
        @ValidUsername
        String username,

        @JsonProperty(required = true)
        @NotNull
        @NotBlank
        @Size(min = 2, max = 30)
        String name,

        @JsonProperty(required = true)
        @NotNull
        @NotBlank
        @Size(min = 2, max = 30)
        String surname,

        @JsonProperty(required = true)
        @NotNull
        @ValidEmail
        String email,

        @Nullable
        @NotBlank
        String bio
) {
}
