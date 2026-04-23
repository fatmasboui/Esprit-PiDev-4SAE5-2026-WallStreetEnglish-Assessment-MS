package com.example.certification.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.example.certification.entity.Certificate;
import com.example.certification.service.CertificateService;

@RestController
@RequestMapping("/certificates")
public class CertificateController {

    @Autowired
    private CertificateService service;

    // --- READ ALL ---
    @GetMapping
    public List<Certificate> getAll() {
        return service.getAll();
    }

    // --- READ BY ID ---
    @GetMapping("/{id}")
    public Optional<Certificate> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // --- CREATE ---
    @PostMapping
    public Certificate create(@RequestBody Certificate c) {
        return service.save(c);
    }

    // --- UPDATE ---
    @PutMapping("/{id}")
    public Certificate update(@PathVariable Long id, @RequestBody Certificate c) {
        Optional<Certificate> existing = service.getById(id);
        if (existing.isPresent()) {
            Certificate cert = existing.get();
            cert.setUserId(c.getUserId());
            cert.setCertificateName(c.getCertificateName());
            cert.setLevel(c.getLevel());
            cert.setDateIssued(c.getDateIssued() != null ? c.getDateIssued() : cert.getDateIssued());
            return service.save(cert);
        } else {
            throw new RuntimeException("Certificate not found with id " + id);
        }
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Certificate deleted with id " + id;
    }
}
