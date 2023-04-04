package com.example.myinsta.controller.advice;

import com.example.myinsta.exceptions.ObjectNotValidException;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ProblemDetail handleValidationException(ObjectNotValidException ex) {
        ProblemDetail detail =  ProblemDetail.forStatusAndDetail(
                BAD_REQUEST, "The request parameters failed to validate");
        detail.setProperty("errors", ex.getErrorMessages());
        return detail;
    }

}
