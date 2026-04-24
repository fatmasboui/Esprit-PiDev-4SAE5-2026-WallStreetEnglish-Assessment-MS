package com.example.certification.controller;

import com.example.certification.entity.Question;
import com.example.certification.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService service;

    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        return ResponseEntity.ok(service.getAllQuestions());
    }

    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        return ResponseEntity.ok(service.save(question));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        Question updated = service.updateQuestion(id, question);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        service.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}