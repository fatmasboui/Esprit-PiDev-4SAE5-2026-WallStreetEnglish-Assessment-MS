package com.example.certification.controller;

import com.example.certification.entity.CertificationExam;
import com.example.certification.entity.Question;
import com.example.certification.service.CertificationExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/certification-exams")
@CrossOrigin(origins = "http://localhost:4200")
public class CertificationExamController {

    @Autowired
    private CertificationExamService service;

    @GetMapping
    public List<CertificationExam> getAll(@RequestParam(required = false) Long certificationId) {
        if (certificationId != null) {
            return service.getExamsByCertificationId(certificationId);
        }
        return service.getAllExams(); 
    }

    @GetMapping("/certification/{certificationId}")
    public List<CertificationExam> getExamsByCertificationId(@PathVariable Long certificationId) {
        return service.getExamsByCertificationId(certificationId);
    }

    @GetMapping("/{id}")
    public CertificationExam getById(@PathVariable Long id) {
        return service.getExamById(id);
    }

    @PostMapping
    public CertificationExam create(@RequestBody CertificationExam exam) {
        return service.saveExam(exam);
    }

    @PutMapping("/{id}")
    public CertificationExam update(@PathVariable Long id, @RequestBody CertificationExam exam) {
        return service.updateExam(id, exam);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteExam(id);
    }
}
