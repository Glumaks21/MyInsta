package ua.glumaks.rest.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ua.glumaks.rest.annotations.PasswordMatches;
import ua.glumaks.rest.payload.request.SignupRequest;

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
