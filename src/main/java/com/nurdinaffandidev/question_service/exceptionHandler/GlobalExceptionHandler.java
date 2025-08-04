package com.nurdinaffandidev.question_service.exceptionHandler;

import com.nurdinaffandidev.question_service.exception.CategoryNotFoundException;
import com.nurdinaffandidev.question_service.exception.QuestionNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiError> handleCategoryNotFound(CategoryNotFoundException exception) {
        ApiError error = new ApiError(
                            exception.getMessage(),
                            HttpStatus.NOT_FOUND.value(),
                            LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(QuestionNotFoundException.class)
    public ResponseEntity<ApiError> handleQuestionNotFound(QuestionNotFoundException exception) {
        ApiError error = new ApiError(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception exception) {
        return new ResponseEntity<>("Internal Server Error: " + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
