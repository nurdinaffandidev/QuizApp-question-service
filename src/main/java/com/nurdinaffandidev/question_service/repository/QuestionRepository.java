package com.nurdinaffandidev.question_service.repository;

import com.nurdinaffandidev.question_service.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM question q WHERE q.category=:category ORDER BY RANDOM() LIMIT :numQuestions", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQuestions);
}
