package com.example.myinsta.payload.request;

import com.example.myinsta.annotations.PasswordMatches;
import com.example.myinsta.annotations.ValidEmail;
import com.example.myinsta.annotations.ValidPassword;
import jakarta.validation.constraints.*;

@PasswordMatches(message = "{password-confirmation}")
public record SignupRequest(
        @NotNull(message = "{name.not-null}")
        @NotBlank(message = "{name.not-blank}")
        @Size(min = 2, max = 30, message = "{name.size}")
        String name,

        @NotNull(message = "{surname.not-null}")
        @NotBlank(message = "{surname.not-blank}")
        @Size(min = 2, max = 30, message = "{surname.size}")
        String surname,

        @NotNull(message = "{email.not-null}")
        @ValidEmail(message = "{email.valid-email}")
        String email,

        @NotNull(message = "{username.not-null}")
        @NotBlank(message = "{username.not-blank}")
        @Size(min = 6, max = 20, message = "{username.size}")
        String username,

        @NotNull(message = "{password.not-null}")
        @ValidPassword(message = "{password.valid-password}")
        @Size(min = 9, max = 20, message = "{password.size}")
        String password,

        String passwordConfirmation
){
}
