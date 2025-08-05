package com.nurdinaffandidev.question_service.exception;

/**
 * Exception thrown when a requested question is not found.
 */
public class QuestionNotFoundException extends RuntimeException {

    /**
     * Constructs a new QuestionNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
