package com.example.myinsta.validators;

import com.example.myinsta.exceptions.ObjectNotValidException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.Map;
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
        if (!violations.isEmpty()) {
            var errorMessages = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
            throw new ObjectNotValidException(errorMessages);
        }
    }


}
