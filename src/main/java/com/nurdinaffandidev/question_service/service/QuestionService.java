package com.nurdinaffandidev.question_service.service;

import com.nurdinaffandidev.question_service.exception.CategoryNotFoundException;
import com.nurdinaffandidev.question_service.exception.QuestionNotFoundException;
import com.nurdinaffandidev.question_service.model.Question;
import com.nurdinaffandidev.question_service.model.QuestionWrapper;
import com.nurdinaffandidev.question_service.model.QuizResponse;
import com.nurdinaffandidev.question_service.repository.QuestionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    public List<Question> getQuestionsByCategory(String category) {
        List<Question> questions = questionRepository.findByCategory(category);
        if (questions == null || questions.isEmpty()) {
            throw new CategoryNotFoundException("No questions found for category: " + category);
        }
        return questions;
    }

    public Question getQuestionById(int id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id= " + id + " not found."));
    }

    public Question addQuestion(@Valid Question question) {
        return questionRepository.save(question);
    }

    public Question deleteQuestion(int id) {
        Question questionToDelete = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id= " + id + " not found."));
        questionRepository.deleteById(id);
        return questionToDelete;
    }

    public List<Question> generateQuestions(String category, int numQuestions) {
        List<Integer> questionsId = questionRepository.findRandomQuestionsByCategory(category, numQuestions);
        List<Question> questions = new ArrayList<>();

        for(int id : questionsId) {
            questions.add(getQuestionById(id));
        }

        return questions;
    }

    public List<QuestionWrapper> generateWrapperQuestions(String category, int numQuestions) {
        List<Integer> questionsId = questionRepository.findRandomQuestionsByCategory(category, numQuestions);
        List<QuestionWrapper> questions = new ArrayList<>();

        for(int id : questionsId) {
            Question question = getQuestionById(id);
            QuestionWrapper wrappedQn = new QuestionWrapper(
                    question.getId(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4(),
                    question.getQuestionTitle()
            );
            questions.add(wrappedQn);
        }

        return questions;
    }

    public Integer getScore(List<QuizResponse> responses) {
        int correctAnswer = 0;

        for(QuizResponse response : responses) {
            int questionId = response.getQuestionId();

            Question questionToCheck = questionRepository.findById(questionId)
                    .orElseThrow(() -> new QuestionNotFoundException("Question with id= " + questionId + " not found."));

            if (response.getResponse().equals(questionToCheck.getCorrectAnswer())) {
                correctAnswer++;
            }
        }
        return correctAnswer;
    }
}
