package com.nurdinaffandidev.question_service.exceptionHandler;

import com.nurdinaffandidev.question_service.exception.CategoryNotFoundException;
import com.nurdinaffandidev.question_service.exception.QuestionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Global exception handler for handling and customizing API error responses.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles exceptions when a requested category is not found.
     *
     * @param exception the CategoryNotFoundException thrown
     * @return ResponseEntity containing ApiError with 404 status
     */
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFound(CategoryNotFoundException exception) {
        // Create ApiError with message, status code and current timestamp
        ApiError error = new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles exceptions when a requested question is not found.
     *
     * @param exception the QuestionNotFoundException thrown
     * @return ResponseEntity containing ApiError with 404 status
     */
    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ApiError> handleQuestionNotFound(QuestionNotFoundException exception) {
        // Create ApiError with message, status code and current timestamp
        ApiError error = new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles all other uncaught exceptions (generic fallback).
     *
     * @param exception the Exception thrown
     * @return ResponseEntity with a simple error message and 500 status
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception exception) {
        // Return a simple string message with HTTP 500 Internal Server Error
        return new ResponseEntity<>("Internal Server Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
