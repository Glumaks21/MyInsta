package com.example.myinsta.controller.advice;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ua.glumaks.rest.exception.ObjectNotValidException;
import ua.glumaks.rest.exception.ResourceForbiddenException;
import ua.glumaks.rest.exception.ResourceNotFoundException;
import ua.glumaks.rest.exception.UserNotAuthenticatedException;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ProblemDetail handleValidationException(ObjectNotValidException ex) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                BAD_REQUEST, "The request parameters failed to validate");
        detail.setProperty("errors", ex.getErrorMessages());
        return detail;
    }

    @ExceptionHandler
    ProblemDetail handleResourceNotFound(ResourceNotFoundException ex) {
        return ProblemDetail.forStatusAndDetail(BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler
    ProblemDetail handleResourceForbiddenException(ResourceForbiddenException ex) {
        return ProblemDetail.forStatusAndDetail(FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler
    ProblemDetail handleUserNotAuthenticatedException(UserNotAuthenticatedException ex) {
        return ProblemDetail.forStatusAndDetail(UNAUTHORIZED, ex.getMessage());
    }

}
