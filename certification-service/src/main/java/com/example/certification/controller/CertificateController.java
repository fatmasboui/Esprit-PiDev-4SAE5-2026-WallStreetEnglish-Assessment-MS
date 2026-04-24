package com.example.certification.controller;

import com.example.certification.entity.Certificate;
import com.example.certification.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/certificates")
@RequiredArgsConstructor
@CrossOrigin(origins ="http://localhost:4200")
public class CertificateController {

    private final CertificateService service;

    @GetMapping
    public ResponseEntity<List<Certificate>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Certificate> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Certificate> create(@RequestBody Certificate c) {
        return ResponseEntity.ok(service.save(c));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Certificate> update(@PathVariable Long id, @RequestBody Certificate c) {
        return service.getById(id).map(cert -> {
            cert.setUserId(c.getUserId());
            cert.setCertificateName(c.getCertificateName());
            cert.setLevel(c.getLevel());
            cert.setDateIssued(c.getDateIssued() != null ? c.getDateIssued() : cert.getDateIssued());
            return ResponseEntity.ok(service.save(cert));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
