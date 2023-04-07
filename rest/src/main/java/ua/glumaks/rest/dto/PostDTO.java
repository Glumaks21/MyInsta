package ua.glumaks.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import ua.glumaks.rest.annotations.ValidUsername;


public record PostDTO(

        @JsonProperty(required = true)
        @NotNull
        Long id,

        @JsonProperty(required = true)
        @NotNull
        @NotBlank
        @Size(max = 256)
        String title,

        @JsonProperty(required = true)
        @NotNull
        @NotBlank
        @Size(max = 2048)
        String body,

        @JsonProperty(required = true)
        @NotNull
        @NotBlank
        @ValidUsername
        String author,

        @JsonProperty(required = true)
        @Nullable
        @Size(max = 64)
        String location,

        @PositiveOrZero
        Integer likes
) {
}
