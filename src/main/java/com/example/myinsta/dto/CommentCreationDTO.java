package com.example.myinsta.dto;

import com.example.myinsta.annotations.ValidUsername;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CommentCreationDTO(

        @JsonProperty(required = true)
        @NotNull
        @NotBlank
        @Size(max = 2048)
        String message,

        @JsonProperty(required = true)
        @NotNull
        Long postId,

        @JsonProperty(required = true)
        @NotNull
        @NotBlank
        @ValidUsername
        String author
) {
}
