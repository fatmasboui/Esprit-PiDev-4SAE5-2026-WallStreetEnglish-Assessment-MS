package com.example.assessment.controller;

import com.example.assessment.entity.Attempt;
import com.example.assessment.service.AttemptService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attempts")
public class AttemptController {

    private final AttemptService attemptService;

    public AttemptController(AttemptService attemptService) {
        this.attemptService = attemptService;
    }

    // UPDATE Attempt
    @PutMapping("/{id}")
    public Attempt update(@PathVariable Long id, @RequestBody Attempt attempt) {
        attempt.setId(id);
        return attemptService.update(attempt);
    }

    // DELETE Attempt
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        attemptService.delete(id);
        return "Attempt deleted successfully";
    }

    // GET ALL
    @GetMapping
    public List<Attempt> getAll() {
        return attemptService.getAll();
    }

    // CREATE
    @PostMapping
    public Attempt create(@RequestBody Attempt a) {
        return attemptService.save(a);
    }
}
