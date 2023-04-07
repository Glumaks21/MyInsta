package ua.glumaks.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.glumaks.rest.annotations.ValidUsername;

public record CommentDTO(

        @JsonProperty(required = true)
        @NotNull
        Long id,

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
