package com.example.certification.controller;

import com.example.certification.entity.Certification;
import com.example.certification.entity.Question;
import com.example.certification.service.CertificationService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/certifications")
@CrossOrigin(origins ="http://localhost:4200")

public class CertificationController {

    private final CertificationService service;

    public CertificationController(CertificationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Certification> getAll() {
        return service.getAllCertifications();
    }

    @GetMapping("/{id}")
    public Certification getById(@PathVariable Long id) {
        return service.getCertificationById(id).orElse(null);
    }

    @GetMapping("/{id}/questions")
    public List<Question> getQuestionsByCertification(@PathVariable Long id) {
        return service.getQuestionsByCertification(id);
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Certification> create(@RequestBody Certification cert) {
        try {
            System.out.println("=== Création certification ===");
            System.out.println("Titre: " + cert.getTitle());
            System.out.println("Niveau: " + cert.getLevel());

            Certification saved = service.createCertification(cert);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public Certification update(@PathVariable Long id, @RequestBody Certification cert) {
        return service.updateCertification(id, cert);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteCertification(id);
    }
}