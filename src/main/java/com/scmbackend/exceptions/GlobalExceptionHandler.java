package com.scmbackend.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String,String>> handleConstraintViolation(ConstraintViolationException exception){
        Map<String,String> errors = new LinkedHashMap<>();

        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();

        constraintViolations.forEach(constraintViolation -> {
            logger.info("Global Exception class use to handle validation exceptions like(user name is required).");
            String propertyName = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            errors.put(propertyName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
