package com.nurdinaffandidev.question_service.service;

import com.nurdinaffandidev.question_service.exception.CategoryNotFoundException;
import com.nurdinaffandidev.question_service.exception.QuestionNotFoundException;
import com.nurdinaffandidev.question_service.model.Question;
import com.nurdinaffandidev.question_service.model.QuestionWrapper;
import com.nurdinaffandidev.question_service.model.QuizResponse;
import com.nurdinaffandidev.question_service.repository.QuestionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    Environment environment; // spring environment

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

    public List<Integer> generateQuestionIds(String category, int numQuestions) {
        return questionRepository.findRandomQuestionsByCategory(category, numQuestions);
    }

    public List<QuestionWrapper> getWrapperQuestions(List<Integer> questionIds) {
        System.out.println("Port called = " + environment.getProperty("local.server.port")); // to check load balancing feature
        List<QuestionWrapper> wrappedQns = new ArrayList<>();

        for(int id : questionIds) {
            Question question = getQuestionById(id);
            QuestionWrapper wrappedQn = new QuestionWrapper(
                    question.getId(),
                    question.getOption1(),
                    question.getOption2(),
                    question.getOption3(),
                    question.getOption4(),
                    question.getQuestionTitle()
            );
            wrappedQns.add(wrappedQn);
        }

        return wrappedQns;
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
