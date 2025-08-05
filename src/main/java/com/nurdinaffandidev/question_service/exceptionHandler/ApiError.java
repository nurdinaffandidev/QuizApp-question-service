package com.nurdinaffandidev.question_service.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Represents a structured error response for API exceptions.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    // Error message describing what went wrong
    private String message;

    // HTTP status code representing the error type (e.g., 404, 500)
    private int status;

    // Timestamp of when the error occurred
    private LocalDateTime timestamp;
}
