package com.nurdinaffandidev.question_service.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message ) {
        super(message);
    }
}
