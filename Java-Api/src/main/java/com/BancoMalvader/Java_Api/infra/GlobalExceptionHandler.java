package com.BancoMalvader.Java_Api.infra;

import com.BancoMalvader.Java_Api.exceptions.HandleValidation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        HandleValidation validationException = new HandleValidation(ex);
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(validationException.getErrors());
    }
}
