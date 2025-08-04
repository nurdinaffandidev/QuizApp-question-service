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

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping(value = "/", params = "!id")
    public String greet(HttpServletRequest request) {
        return "Welcome To Question Service Controller";
    }

    // get all questions
    @GetMapping("/allQuestions")
    public ResponseEntity<List<Question>> getAllQuestion() {
        return new ResponseEntity<>(questionService.getAllQuestion(), HttpStatus.OK);
    }

    // get questions by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category) {
        return new ResponseEntity<>(questionService.getQuestionsByCategory(category), HttpStatus.OK);
    }

    // get question by id
    @GetMapping(value = "/", params = "id")
    public ResponseEntity<Question> getQuestionById(@RequestParam int id) {
        return new ResponseEntity<>(questionService.getQuestionById(id), HttpStatus.OK);
    }

    // add question
    @PostMapping("/add-question")
    public ResponseEntity<String> addQuestion(@Valid @RequestBody Question question) {
        Question addedQuestion = questionService.addQuestion(question);
        return new ResponseEntity<>("Question successfully added, id: " + addedQuestion.getId(), HttpStatus.CREATED);
    }

    // delete question
    @DeleteMapping("/{id}")
    public ResponseEntity<Question> deleteQuestion(@PathVariable int id) {
        Question questionToDelete = questionService.deleteQuestion(id);
        return new ResponseEntity<>(questionToDelete, HttpStatus.OK);
    }

    // generate questions
    @GetMapping("/generate-questions")
    public ResponseEntity<List<Question>> generateQuestions(@RequestParam String category, @RequestParam int numQuestions) {
        return new ResponseEntity<>(questionService.generateQuestions(category, numQuestions), HttpStatus.OK);
    }

    // generate wrapper questions
    @GetMapping("/generate-wrapper-questions")
    public ResponseEntity<List<QuestionWrapper>> generateWrapperQuestions(@RequestParam String category, @RequestParam int numQuestions) {
        return new ResponseEntity<>(questionService.generateWrapperQuestions(category, numQuestions), HttpStatus.OK);
    }

    // get score
    @PostMapping("/get-score")
    public ResponseEntity<Integer> getScore(@RequestBody List<QuizResponse> responses) {
        return new ResponseEntity<>(questionService.getScore(responses), HttpStatus.OK);
    }
}
