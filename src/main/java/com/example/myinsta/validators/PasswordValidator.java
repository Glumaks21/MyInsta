package com.example.myinsta.validators;

import com.example.myinsta.annotations.ValidPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final static String REGEXP = ".{9,20}";

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password != null && Pattern.compile(REGEXP)
                .matcher(password)
                .matches();
    }

}
