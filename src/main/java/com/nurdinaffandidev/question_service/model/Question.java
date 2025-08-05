package com.nurdinaffandidev.question_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a Question in the question service.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {

    /** Unique identifier for the question. Auto-generated. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** Category of the question, e.g., "Java", "Math". */
    private String category;

    /** Difficulty level of the question, e.g., "Easy", "Medium", "Hard". */
    private String difficulty;

    /** First answer option. */
    private String option1;

    /** Second answer option. */
    private String option2;

    /** Third answer option. */
    private String option3;

    /** Fourth answer option. */
    private String option4;

    /** The text/title of the question being asked. */
    private String questionTitle;

    /** The correct answer among the options. */
    private String correctAnswer;
}
