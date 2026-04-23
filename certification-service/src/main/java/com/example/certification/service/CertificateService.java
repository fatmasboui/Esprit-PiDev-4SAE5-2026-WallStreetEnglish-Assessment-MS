package com.example.certification.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;

import com.example.certification.entity.Certificate;
import com.example.certification.repository.CertificateRepository;

@Service
public class CertificateService {

    @Autowired
    private CertificateRepository repo;

    // --- Récupérer tous les certificats ---
    public List<Certificate> getAll() {
        return repo.findAll();
    }

    // --- Récupérer un certificat par ID ---
    public Optional<Certificate> getById(Long id) {
        return repo.findById(id);
    }

    // --- Ajouter ou mettre à jour un certificat ---
    public Certificate save(Certificate c) {
        return repo.save(c);
    }

    // --- Supprimer un certificat par ID ---
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
