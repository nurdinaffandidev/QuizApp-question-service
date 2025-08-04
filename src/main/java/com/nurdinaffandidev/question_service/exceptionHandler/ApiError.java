package com.nurdinaffandidev.question_service.exceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    // Attributes
    private String message;
    private int status;
    private LocalDateTime timestamp;
}
