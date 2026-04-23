package com.example.certification.service;

import com.example.certification.entity.CertificationResult;
import com.example.certification.repository.CertificationResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertificationResultService {

    private final CertificationResultRepository repository;

    // ✅ Constructor injection
    public CertificationResultService(CertificationResultRepository repository) {
        this.repository = repository;
    }

    // ✅ Récupérer tous les résultats
    public List<CertificationResult> getAllResults() {
        return repository.findAll();
    }

    // ✅ Récupérer un résultat par ID
    public CertificationResult getResultById(Long id) {
        return repository.findById(id).orElse(null);
    }

    // ✅ Récupérer tous les résultats d'un user (par userId)
    public List<CertificationResult> getResultsByUserId(Long userId) {
        return repository.findAll().stream()
                .filter(r -> r.getUserId() != null && r.getUserId().equals(userId))
                .toList();
    }

    // ✅ Créer un résultat
    public CertificationResult saveResult(CertificationResult result) {
        return repository.save(result);
    }

    // ✅ Mettre à jour un résultat
    public CertificationResult updateResult(Long id, CertificationResult resDetails) {
        CertificationResult result = getResultById(id);
        if (result != null) {
            result.setScore(resDetails.getScore());
            result.setPassed(resDetails.isPassed());
            result.setUserId(resDetails.getUserId());
            result.setCertificationExam(resDetails.getCertificationExam());
            return repository.save(result);
        }
        return null;
    }

    // ✅ Supprimer un résultat
    public void deleteResult(Long id) {
        repository.deleteById(id);
    }
}