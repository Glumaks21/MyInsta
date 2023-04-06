package com.example.myinsta.validators;

import com.example.myinsta.exception.ObjectNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ObjectsValidator {

    private final Validator validator;


    public ObjectsValidator(ValidatorFactory validatorFactory) {
        validator = validatorFactory.getValidator();
    }

    public ObjectsValidator() {
        this(Validation.buildDefaultValidatorFactory());
    }

    public <T> void validate(T objectToValidate) {
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);
        throwIfViolated(violations);
    }

    private <T> void throwIfViolated(Set<ConstraintViolation<T>> violations) {
        if (!violations.isEmpty()) {
            var errorMessages = getErrorMessages(violations);
            throw new ObjectNotValidException(errorMessages);
        }
    }

    private <T> Set<String> getErrorMessages(Set<ConstraintViolation<T>> violations) {
        return violations.stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toSet());
    }

}
