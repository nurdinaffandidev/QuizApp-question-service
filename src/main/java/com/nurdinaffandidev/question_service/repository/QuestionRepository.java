package com.nurdinaffandidev.question_service.repository;

import com.nurdinaffandidev.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Repository interface for managing {@link Question} entities.
 * Extends JpaRepository to provide CRUD operations.
 */
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    /**
     * Finds all questions matching the given category.
     *
     * @param category the category to filter questions by
     * @return a list of questions in the specified category
     */
    List<Question> findByCategory(String category);

    /**
     * Retrieves a list of random question IDs filtered by category.
     * Uses native SQL query with ORDER BY RANDOM() and LIMIT.
     *
     * @param category the category to filter questions by
     * @param numQuestions the number of random question IDs to retrieve
     * @return a list of randomly selected question IDs for the category
     */
    @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQuestions", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQuestions);
}
