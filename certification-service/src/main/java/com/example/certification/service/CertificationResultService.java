package com.example.certification.service;

import com.example.certification.entity.CertificationResult;
import com.example.certification.repository.CertificationResultRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificationResultService {

    private final CertificationResultRepository repository;

    public List<CertificationResult> getAllResults() {
        log.info("Fetching all certification results");
        return repository.findAll();
    }

    public CertificationResult getResultById(Long id) {
        log.info("Fetching certification result with id: {}", id);
        return repository.findById(id).orElse(null);
    }

    public List<CertificationResult> getResultsByUserId(Long userId) {
        log.info("Fetching certification results for user id: {}", userId);
        return repository.findAll().stream()
                .filter(r -> r.getUserId() != null && r.getUserId().equals(userId))
                .toList();
    }

    public CertificationResult saveResult(CertificationResult result) {
        log.info("Saving new certification result");
        return repository.save(result);
    }

    public CertificationResult updateResult(Long id, CertificationResult resDetails) {
        log.info("Updating certification result with id: {}", id);
        return repository.findById(id).map(result -> {
            result.setScore(resDetails.getScore());
            result.setPassed(resDetails.isPassed());
            result.setUserId(resDetails.getUserId());
            result.setCertificationExam(resDetails.getCertificationExam());
            return repository.save(result);
        }).orElse(null);
    }

    public void deleteResult(Long id) {
        log.info("Deleting certification result with id: {}", id);
        repository.deleteById(id);
    }
}