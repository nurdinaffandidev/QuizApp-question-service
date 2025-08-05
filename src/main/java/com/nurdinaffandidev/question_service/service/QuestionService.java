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

/**
 * Service class responsible for managing Question-related business logic.
 */
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    Environment environment; // Spring environment to access properties, e.g. server port

    /**
     * Constructor for QuestionService with repository injection.
     * @param questionRepository repository for Question entities
     */
    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Retrieves all questions from the database.
     * @return list of all questions
     */
    public List<Question> getAllQuestion() {
        return questionRepository.findAll();
    }

    /**
     * Retrieves questions filtered by category.
     * Throws CategoryNotFoundException if no questions found for given category.
     * @param category the category name to filter questions
     * @return list of questions matching the category
     */
    public List<Question> getQuestionsByCategory(String category) {
        List<Question> questions = questionRepository.findByCategory(category);
        if (questions == null || questions.isEmpty()) {
            throw new CategoryNotFoundException("No questions found for category: " + category);
        }
        return questions;
    }

    /**
     * Retrieves a question by its ID.
     * Throws QuestionNotFoundException if question does not exist.
     * @param id question ID
     * @return Question object
     */
    public Question getQuestionById(int id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id= " + id + " not found."));
    }

    /**
     * Adds a new question to the database.
     * @param question the Question object to add (validated)
     * @return the saved Question object
     */
    public Question addQuestion(@Valid Question question) {
        return questionRepository.save(question);
    }

    /**
     * Deletes a question by ID.
     * Throws QuestionNotFoundException if question not found.
     * @param id the question ID to delete
     * @return the deleted Question object
     */
    public Question deleteQuestion(int id) {
        Question questionToDelete = questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question with id= " + id + " not found."));
        questionRepository.deleteById(id);
        return questionToDelete;
    }

    /**
     * Generates a list of questions randomly selected by category and count.
     * @param category the category to filter questions
     * @param numQuestions number of questions to generate
     * @return list of randomly selected Question objects
     */
    public List<Question> generateQuestions(String category, int numQuestions) {
        List<Integer> questionsId = questionRepository.findRandomQuestionsByCategory(category, numQuestions);
        List<Question> questions = new ArrayList<>();

        for(int id : questionsId) {
            questions.add(getQuestionById(id));
        }

        return questions;
    }

    /**
     * Generates a list of question IDs randomly selected by category and count.
     * @param category the category to filter questions
     * @param numQuestions number of question IDs to generate
     * @return list of question IDs
     */
    public List<Integer> generateQuestionIds(String category, int numQuestions) {
        return questionRepository.findRandomQuestionsByCategory(category, numQuestions);
    }

    /**
     * Retrieves a list of QuestionWrapper DTOs for given question IDs.
     * Prints the server port to verify load balancing in a microservices environment.
     * @param questionIds list of question IDs to retrieve
     * @return list of QuestionWrapper objects (simplified question view)
     */
    public List<QuestionWrapper> getWrapperQuestions(List<Integer> questionIds) {
        System.out.println("Port called = " + environment.getProperty("local.server.port")); // For load balancing trace
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

    /**
     * Calculates the score (number of correct answers) based on user responses.
     * @param responses list of QuizResponse objects containing user answers
     * @return number of correct answers
     */
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
