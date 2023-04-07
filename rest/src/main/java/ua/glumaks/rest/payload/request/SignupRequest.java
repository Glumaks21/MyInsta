package ua.glumaks.rest.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ua.glumaks.rest.annotations.PasswordMatches;
import ua.glumaks.rest.annotations.ValidEmail;
import ua.glumaks.rest.annotations.ValidPassword;

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
