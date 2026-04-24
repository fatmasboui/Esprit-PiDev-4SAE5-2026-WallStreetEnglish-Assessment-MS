package com.example.certification.controller;

import com.example.certification.entity.Certification;
import com.example.certification.entity.Question;
import com.example.certification.service.CertificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certifications")
@CrossOrigin(origins ="http://localhost:4200")
@RequiredArgsConstructor
@Slf4j
public class CertificationController {

    private final CertificationService service;

    @GetMapping
    public ResponseEntity<List<Certification>> getAll() {
        return ResponseEntity.ok(service.getAllCertifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certification> getById(@PathVariable Long id) {
        return service.getCertificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/questions")
    public ResponseEntity<List<Question>> getQuestionsByCertification(@PathVariable Long id) {
        return ResponseEntity.ok(service.getQuestionsByCertification(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Certification> create(@RequestBody Certification cert) {
        try {
            log.info("Creating certification: {}", cert.getTitle());
            Certification saved = service.saveCertification(cert);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (Exception e) {
            log.error("Error creating certification", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certification> update(@PathVariable Long id, @RequestBody Certification cert) {
        Certification updated = service.updateCertification(id, cert);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.deleteCertification(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }
}