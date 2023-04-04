package com.example.myinsta.validators;

import com.example.myinsta.annotations.PasswordMatches;
import com.example.myinsta.payload.request.SignupRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        if (object instanceof SignupRequest request) {
            return request.password() != null &&
                    request.password().equals(request.passwordConfirmation());
        }

        return false;
    }

}
