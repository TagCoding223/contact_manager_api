package com.scmbackend.exceptions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;

import io.jsonwebtoken.JwtException;
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

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String,String>> handleAuthenticationException(AuthenticationException authenticationException){
        Map<String,String> responce = new HashMap<>();
        responce.put("error", "Authentication Failed.");
        responce.put("message", authenticationException.getMessage());
        return new ResponseEntity<>(responce, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Map<String,String>> handleJwtException(JwtException jwtException){
        Map<String,String> responce = new HashMap<>();
        responce.put("error", "Invaild JWT Token.");
        responce.put("message", jwtException.getMessage());
        return new ResponseEntity<>(responce, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,String>> handleGenericException(Exception exception){
        Map<String,String> responce = new HashMap<>();
        responce.put("error", "Internal server error.");
        responce.put("message", exception.getMessage());
        return new ResponseEntity<>(responce, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // @ExceptionHandler(value = MultipartException.class)
    // @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    // @ResponseBody
    // public String handleFileUploadingError(MultipartException exception) {
    //     logger.warn("Failed to upload attachment", exception);
    //     return exception.getMessage();
    // }
}
