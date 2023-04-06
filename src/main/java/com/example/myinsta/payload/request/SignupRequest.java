package com.example.myinsta.payload.request;

import com.example.myinsta.annotations.PasswordMatches;
import com.example.myinsta.annotations.ValidEmail;
import com.example.myinsta.annotations.ValidPassword;
import jakarta.validation.constraints.*;

@PasswordMatches
public record SignupRequest(

        @NotNull
        @NotBlank
        @Size(min = 2, max = 30)
        String name,

        @NotNull
        @NotBlank
        @Size(min = 2, max = 30)
        String surname,

        @NotNull
        @ValidEmail
        String email,

        @NotNull
        @NotBlank
        @Size(min = 6, max = 20)
        String username,

        @NotNull
        @ValidPassword
        @Size(min = 9, max = 20)
        String password,

        String passwordConfirmation
){
}
