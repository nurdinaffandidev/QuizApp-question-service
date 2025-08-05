package com.nurdinaffandidev.question_service.exception;

/**
 * Exception thrown when a requested question category is not found.
 */
public class CategoryNotFoundException extends RuntimeException {

    /**
     * Constructs a new CategoryNotFoundException with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
