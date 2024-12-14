package com.BancoMalvader.Java_Api.exceptions;

import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.HashMap;
import java.util.Map;

@Getter
public class HandleValidation extends RuntimeException {
    private final Map<String, String> errors;

    public HandleValidation(MethodArgumentNotValidException ex) {
        super(createErrorMessage(ex));
        this.errors = extractErrors(ex);
    }

    private static String createErrorMessage(MethodArgumentNotValidException ex) {
        Map<String, String> errors = extractErrors(ex);

        StringBuilder errorMessageBuilder = new StringBuilder("Validation failed for fields:\n");
        errors.forEach((field, message) ->
                errorMessageBuilder.append(String.format("Field '%s': %s%n", field, message))
        );

        return errorMessageBuilder.toString();
    }

    private static Map<String, String> extractErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
