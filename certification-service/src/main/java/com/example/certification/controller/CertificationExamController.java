package com.example.certification.controller;

import com.example.certification.entity.CertificationExam;
import com.example.certification.service.CertificationExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certification-exams")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CertificationExamController {

    private final CertificationExamService service;

    @GetMapping
    public ResponseEntity<List<CertificationExam>> getAll(@RequestParam(required = false) Long certificationId) {
        if (certificationId != null) {
            return ResponseEntity.ok(service.getExamsByCertificationId(certificationId));
        }
        return ResponseEntity.ok(service.getAllExams());
    }

    @GetMapping("/certification/{certificationId}")
    public ResponseEntity<List<CertificationExam>> getExamsByCertificationId(@PathVariable Long certificationId) {
        return ResponseEntity.ok(service.getExamsByCertificationId(certificationId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationExam> getById(@PathVariable Long id) {
        CertificationExam exam = service.getExamById(id);
        return exam != null ? ResponseEntity.ok(exam) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CertificationExam> create(@RequestBody CertificationExam exam) {
        return ResponseEntity.ok(service.saveExam(exam));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationExam> update(@PathVariable Long id, @RequestBody CertificationExam exam) {
        CertificationExam updated = service.updateExam(id, exam);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteExam(id);
        return ResponseEntity.noContent().build();
    }
}
