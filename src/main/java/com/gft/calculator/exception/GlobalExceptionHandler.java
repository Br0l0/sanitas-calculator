package com.gft.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationError(MethodArgumentNotValidException exception) {
        List<String> details = exception.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .toList();

        return ResponseEntity.badRequest().body(ApiError.of(
                "INVALID_REQUEST",
                "The request contains invalid fields",
                details
        ));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidRequestBody() {
        return ResponseEntity.badRequest().body(ApiError.of(
                "INVALID_REQUEST_BODY",
                "The request body is invalid or malformed",
                List.of("Check the JSON format and supported operation values: ADD, SUBTRACT")
        ));
    }

    @ExceptionHandler(UnsupportedCalculatorOperationException.class)
    public ResponseEntity<ApiError> handleUnsupportedOperation(UnsupportedCalculatorOperationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiError.of(
                "UNSUPPORTED_OPERATION",
                exception.getMessage(),
                List.of("Supported operations are ADD and SUBTRACT")
        ));
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }
}
