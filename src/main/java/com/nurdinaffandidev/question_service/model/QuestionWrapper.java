package com.nurdinaffandidev.question_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * A lightweight wrapper class for questions,
 * typically used to send question details without sensitive data such as the correct answer.
 */
@Data
@AllArgsConstructor
public class QuestionWrapper {

    /** Unique identifier of the question */
    private Integer questionId;

    /** First answer option */
    private String option1;

    /** Second answer option */
    private String option2;

    /** Third answer option */
    private String option3;

    /** Fourth answer option */
    private String option4;

    /** The text/title of the question */
    private String questionTitle;
}
