package com.example.myinsta.controller.advice;

import com.example.myinsta.exception.ObjectNotValidException;
import com.example.myinsta.exception.ResourceForbiddenException;
import com.example.myinsta.exception.ResourceNotFoundException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

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
        return ProblemDetail.forStatusAndDetail(UNAUTHORIZED, ex.getMessage());
    }

}
