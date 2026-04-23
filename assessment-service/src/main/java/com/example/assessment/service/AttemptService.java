package com.example.assessment.service;

import com.example.assessment.entity.Attempt;
import com.example.assessment.repository.AttemptRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttemptService {

    private final AttemptRepository repo;

    public AttemptService(AttemptRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public Attempt save(Attempt attempt) {
        return repo.save(attempt);
    }

    // UPDATE
    public Attempt update(Attempt attempt) {

        if (!repo.existsById(attempt.getId())) {
            throw new IllegalArgumentException(
                    "Attempt not found with id: " + attempt.getId());
        }

        return repo.save(attempt);
    }

    // DELETE
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException(
                    "Attempt not found with id: " + id);
        }
        repo.deleteById(id);
    }

    // READ ALL
    public List<Attempt> getAll() {
        return repo.findAll();
    }

    // READ BY ID
    public Attempt getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Attempt not found with id: " + id));
    }
}
