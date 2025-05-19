package com.technical_test.ms_price.infrastructure.exception;

import com.technical_test.ms_price.infrastructure.api.response.ErrorResponse;
import com.technical_test.ms_price.infrastructure.common.Constants;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.stream.Collectors;

@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                null,
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(WebExchangeBindException ex) {
        String details = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.joining(", "));

        String message = "Invalid input parameters";

        FieldError dateFromFieldError = ex.getBindingResult().getFieldError(Constants.DATEFROM_PARAMETER_NAME);

        if (ex.getBindingResult().hasFieldErrors(Constants.DATEFROM_PARAMETER_NAME) &&
                !Objects.isNull(dateFromFieldError) &&
                !Objects.isNull(dateFromFieldError.getDefaultMessage()) &&
                dateFromFieldError.getDefaultMessage().contains("Failed to convert")) {
            details = Constants.DATEFROM_PARAMETER_NAME + ": " + Constants.MESSAGE_DATEFROM_CORRECT_FORMAT;
        }

        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                message,
                details,
                LocalDateTime.now().toString()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

}
