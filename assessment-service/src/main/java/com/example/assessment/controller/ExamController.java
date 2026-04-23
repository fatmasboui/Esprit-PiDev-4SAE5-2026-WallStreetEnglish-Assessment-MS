package com.example.assessment.controller;

import com.example.assessment.entity.Exam;
import com.example.assessment.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exams")
public class ExamController {

    @Autowired
    private ExamService service;

    @GetMapping
    public List<Exam> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getById(@PathVariable Long id) {
        Exam exam = service.getById(id);
        if (exam == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(exam);
    }

    @PostMapping
    public Exam create(@RequestBody Exam exam) {
        // Pour créer un nouvel examen, ne pas inclure d'ID
        exam.setId(null);
        return service.save(exam);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exam> update(@PathVariable Long id, @RequestBody Exam exam) {
        try {
            exam.setId(id);
            Exam updatedExam = service.updateSafe(id, exam); // Utilisez la version safe
            return ResponseEntity.ok(updatedExam);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}