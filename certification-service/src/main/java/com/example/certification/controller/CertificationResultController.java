package com.example.certification.controller;

import com.example.certification.entity.CertificationResult;
import com.example.certification.service.CertificationResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certification-results")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CertificationResultController {

    private final CertificationResultService service;

    @GetMapping
    public ResponseEntity<List<CertificationResult>> getAll() {
        return ResponseEntity.ok(service.getAllResults());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResult> getById(@PathVariable Long id) {
        CertificationResult result = service.getResultById(id);
        return result != null ? ResponseEntity.ok(result) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CertificationResult>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getResultsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<CertificationResult> save(@RequestBody CertificationResult result) {
        return ResponseEntity.ok(service.saveResult(result));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CertificationResult> update(@PathVariable Long id, @RequestBody CertificationResult result) {
        CertificationResult updated = service.updateResult(id, result);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteResult(id);
        return ResponseEntity.noContent().build();
    }
}
