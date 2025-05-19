package com.technical_test.ms_price.infrastructure.exception;

import com.technical_test.ms_price.infrastructure.common.Constants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(WebExchangeBindException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        if (errors.containsKey(Constants.DATEFROM_PARAMETER_NAME)  && errors.get(Constants.DATEFROM_PARAMETER_NAME).contains("Failed to convert")) {
            errors.put(Constants.DATEFROM_PARAMETER_NAME, Constants.MESSAGE_DATEFROM_CORRECT_FORMAT);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
