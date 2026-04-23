package com.example.certification.controller;

import com.example.certification.entity.Question;
import com.example.certification.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@CrossOrigin(origins = "http://localhost:4200")
public class QuestionController {

    @Autowired
    private QuestionService service;

    // ✅ Récupérer toutes les questions
    @GetMapping
    public ResponseEntity<List<Question>> getAllQuestions() {
        List<Question> questions = service.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    // ✅ Créer une question
    @PostMapping
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        Question saved = service.save(question);
        return ResponseEntity.ok(saved);
    }

    // ✅ Mettre à jour une question
    @PutMapping("/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @RequestBody Question question) {
        Question updated = service.updateQuestion(id, question);
        return ResponseEntity.ok(updated);
    }

    // ✅ Supprimer une question
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        service.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}