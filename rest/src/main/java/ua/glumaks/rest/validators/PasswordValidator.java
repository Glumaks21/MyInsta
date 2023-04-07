package ua.glumaks.rest.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ua.glumaks.rest.annotations.ValidPassword;

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
