package com.example.myinsta.payload.request;

import com.example.myinsta.annotations.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "{username.not-null}")
        @NotBlank(message = "{username.not-blank}")
        @Size(min = 6, max = 20, message = "{username.size}")
        String username,

        @NotNull(message = "{password.not-null}")
        @Size(min = 9, max = 20, message = "{password.size}")
        @ValidPassword(message = "{password.valid-password}")
        String password
) {
}
