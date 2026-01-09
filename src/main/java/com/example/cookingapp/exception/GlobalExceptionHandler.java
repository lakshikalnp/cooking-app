package com.example.cookingapp.exception;

import com.example.cookingapp.util.ErrorCodes;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        ErrorCodes.VALIDATION_ERROR,
                        message,
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI()
                )
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        ErrorCodes.INVALID_ARGUMENT,
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI()
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiErrorResponse(
                        ErrorCodes.INTERNAL_SERVER_ERROR,
                        "Unexpected error occurred",
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        request.getRequestURI()
                )
        );
    }

    @ExceptionHandler(RecipeGenerationException.class)
    public ResponseEntity<ApiErrorResponse> handleRecipeGenerationException(
            RecipeGenerationException ex,
            HttpServletRequest request) {

        return ResponseEntity.badRequest().body(
                new ApiErrorResponse(
                        ex.getErrorCode(),
                        ex.getMessage(),
                        HttpStatus.BAD_REQUEST.value(),
                        request.getRequestURI()
                )
        );
    }
}
