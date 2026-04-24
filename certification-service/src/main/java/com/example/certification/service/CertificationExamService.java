package com.example.certification.service;

import com.example.certification.entity.CertificationExam;
import com.example.certification.repository.CertificationExamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificationExamService {

    private final CertificationExamRepository repository;

    public List<CertificationExam> getAllExams() {
        log.info("Fetching all certification exams");
        return repository.findAll();
    }

    public List<CertificationExam> getExamsByCertificationId(Long certificationId) {
        log.info("Fetching exams for certification id: {}", certificationId);
        return repository.findByCertificationId(certificationId);
    }

    public CertificationExam getExamById(Long id) {
        log.info("Fetching exam with id: {}", id);
        return repository.findById(id).orElse(null);
    }

    public CertificationExam saveExam(CertificationExam exam) {
        log.info("Saving new certification exam");
        return repository.save(exam);
    }

    public CertificationExam updateExam(Long id, CertificationExam examDetails) {
        log.info("Updating exam with id: {}", id);
        return repository.findById(id).map(exam -> {
            exam.setTitle(examDetails.getTitle());
            exam.setDuration(examDetails.getDuration());
            exam.setCertification(examDetails.getCertification());
            return repository.save(exam);
        }).orElse(null);
    }

    public void deleteExam(Long id) {
        log.info("Deleting exam with id: {}", id);
        repository.deleteById(id);
    }
}
