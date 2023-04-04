package com.example.myinsta.validators;

import com.example.myinsta.annotations.ValidEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email != null && Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();
    }
}
