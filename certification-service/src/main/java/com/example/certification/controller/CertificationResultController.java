package com.example.certification.controller;

import com.example.certification.entity.CertificationResult;
import com.example.certification.service.CertificationResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/certification-results")
@CrossOrigin(origins = "http://localhost:4200")
public class CertificationResultController {

    @Autowired
    private CertificationResultService service;

    @GetMapping
    public List<CertificationResult> getAll() {
        return service.getAllResults();
    }

    @GetMapping("/{id}")
    public CertificationResult getById(@PathVariable Long id) {
        return service.getResultById(id);
    }

    @GetMapping("/user/{userId}")
    public List<CertificationResult> getByUser(@PathVariable Long userId) {
        return service.getResultsByUserId(userId);
    }

    @PostMapping
    public CertificationResult save(@RequestBody CertificationResult result) {
        return service.saveResult(result);
    }

    @PutMapping("/{id}")
    public CertificationResult update(@PathVariable Long id, @RequestBody CertificationResult result) {
        return service.updateResult(id, result);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteResult(id);
    }
}
