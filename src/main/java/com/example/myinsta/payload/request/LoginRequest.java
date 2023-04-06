package com.example.myinsta.payload.request;

import com.example.myinsta.annotations.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotNull(message = "{user.username.not-null}")
        @NotBlank(message = "{user.username.not-blank}")
        @Size(min = 6, max = 20, message = "{user.username.size}")
        String username,

        @NotNull(message = "{user.password.not-null}")
        @Size(min = 9, max = 20, message = "{user.password.size}")
        @ValidPassword(message = "{user.password.valid-password}")
        String password
) {
}
