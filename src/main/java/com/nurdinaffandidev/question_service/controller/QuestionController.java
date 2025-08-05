package com.nurdinaffandidev.question_service.controller;

import com.nurdinaffandidev.question_service.model.Question;
import com.nurdinaffandidev.question_service.model.QuestionWrapper;
import com.nurdinaffandidev.question_service.model.QuizResponse;
import com.nurdinaffandidev.question_service.service.QuestionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    private final QuestionService questionService;

    // Constructor-based dependency injection for QuestionService
    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * Greeting endpoint without 'id' parameter.
     * Example: GET /question/
     */
    @GetMapping(value = "/", params = "!id")
    public String greet(HttpServletRequest request) {
        return "Welcome To Question Service Controller";
    }

    /**
     * Get all questions in the system.
     * Example: GET /question/allQuestions
     * @return List of all Question entities with HTTP 200 OK
     */
    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestion() {
        return new ResponseEntity<>(questionService.getAllQuestion(), HttpStatus.OK);
    }

    /**
     * Get questions filtered by category.
     * Example: GET /question/category/{category}
     * @param category Path variable representing question category
     * @return List of questions for the given category with HTTP 200 OK
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
    }

    /**
     * Get a single question by its ID.
     * Example: GET /question/?id=5
     * @param id Query parameter specifying question id
     * @return Question entity with the given id and HTTP 200 OK
     */
    @GetMapping(value = "/", params = "id")
    public ResponseEntity<Question> getQuestionById(@RequestParam int id) {
        return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
    }

    /**
     * Add a new question.
     * Example: POST /question/add-question
     * @param question Request body containing question data (validated)
     * @return Confirmation message with the new question's ID and HTTP 201 CREATED
     */
    @PostMapping("/add-question")
    public ResponseEntity<String> addQuestion(@Valid @RequestBody Question question) {
        Question addedQuestion = questionService.addQuestion(question);
        return new ResponseEntity<>("Question successfully added, id: " + addedQuestion.getId(), HttpStatus.CREATED);
    }

    /**
     * Delete a question by ID.
     * Example: DELETE /question/{id}
     * @param id Path variable for the question to delete
     * @return The deleted Question entity and HTTP 200 OK
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable int id) {
        Question questionToDelete = questionService.deleteQuestion(id);
        return new ResponseEntity<>(questionToDelete, HttpStatus.OK);
    }

    /**
     * Generate a list of questions for a given category and count.
     * Example: GET /question/generate-questions?category=Java&numQuestions=5
     * @param category Query parameter for question category
     * @param numQuestions Query parameter for number of questions to generate
     * @return List of generated questions and HTTP 200 OK
     */
    @GetMapping("/generate-questions")
    public ResponseEntity<List<Question>> generateQuestions(@RequestParam String category, @RequestParam int numQuestions) {
        return new ResponseEntity<>(questionService.generateQuestions(category, numQuestions), HttpStatus.OK);
    }

    /**
     * Generate a list of question IDs for a given category and count.
     * Example: GET /question/generate-question-ids?category=Java&numQuestions=5
     * @param category Query parameter for question category
     * @param numQuestions Query parameter for number of question IDs to generate
     * @return List of question IDs and HTTP 200 OK
     */
    @GetMapping("/generate-question-ids")
    public ResponseEntity<List<Integer>> generateQuestionIds(@RequestParam String category, @RequestParam int numQuestions) {
        return new ResponseEntity<>(questionService.generateQuestionIds(category, numQuestions), HttpStatus.OK);
    }

    /**
     * Retrieve detailed QuestionWrapper objects based on a list of question IDs.
     * Example: POST /question/retrieve-wrapper-questions
     * @param questionIds Request body list of question IDs
     * @return List of QuestionWrapper objects and HTTP 200 OK
     */
    @PostMapping("/retrieve-wrapper-questions")
    public ResponseEntity<List<QuestionWrapper>> retrieveWrapperQuestions(@RequestBody List<Integer> questionIds) {
        return new ResponseEntity<>(questionService.getWrapperQuestions(questionIds), HttpStatus.OK);
    }

    /**
     * Calculate score based on submitted quiz responses.
     * Example: POST /question/get-score
     * @param responses Request body containing list of QuizResponse objects
     * @return Integer score and HTTP 200 OK
     */
    @PostMapping("/get-score")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponse> responses) {
        return new ResponseEntity<>(questionService.getScore(responses), HttpStatus.OK);
    }
}
