package com.nurdinaffandidev.question_service.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Represents a user's response to a quiz question.
 */
@Data
@RequiredArgsConstructor
public class QuizResponse {

    /** The unique identifier of the question being answered */
    private Integer questionId;

    /** The user's answer to the question */
    private String response;
}
