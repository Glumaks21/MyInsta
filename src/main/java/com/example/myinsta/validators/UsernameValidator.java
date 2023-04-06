package com.example.myinsta.validators;

import com.example.myinsta.annotations.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    //TODO regexp
    private static final String regexp = ".{6,20}";

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return username != null && Pattern.compile(regexp)
                .matcher(username)
                .matches();
    }

}
