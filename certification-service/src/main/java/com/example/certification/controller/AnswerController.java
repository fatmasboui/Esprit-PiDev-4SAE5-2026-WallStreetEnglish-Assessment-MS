package com.example.certification.controller;

import com.example.certification.entity.Answer;
import com.example.certification.repository.CertificationResultRepository;
import com.example.certification.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/answers")
@CrossOrigin(origins ="http://localhost:4200")
public class AnswerController {

    @Autowired
    private AnswerService service;
    private CertificationResultRepository repository;

    @GetMapping
    public List<Answer> getAll() {
        return service.getAllAnswers();
    }

    @GetMapping("/{id}")
    public Answer getById(@PathVariable Long id) {
        return service.getAnswerById(id);
    }

    @PostMapping
    public Answer create(@RequestBody Answer answer) {
        return service.saveAnswer(answer);
    }

    @PutMapping("/{id}")
    public Answer update(@PathVariable Long id, @RequestBody Answer answer) {
        return service.updateAnswer(id, answer);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteAnswer(id);
    }
}
