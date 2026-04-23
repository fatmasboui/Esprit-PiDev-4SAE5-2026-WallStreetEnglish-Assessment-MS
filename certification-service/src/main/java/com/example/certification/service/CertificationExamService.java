package com.example.certification.service;

import com.example.certification.entity.CertificationExam;
import com.example.certification.repository.CertificationExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CertificationExamService {

    @Autowired
    private CertificationExamRepository repository;

    public List<CertificationExam> getAllExams() {
        return repository.findAll();
    }

    public List<CertificationExam> getExamsByCertificationId(Long certificationId) {
        return repository.findByCertificationId(certificationId);
    }

    public CertificationExam getExamById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public CertificationExam saveExam(CertificationExam exam) {
        return repository.save(exam);
    }

    public CertificationExam updateExam(Long id, CertificationExam examDetails) {
        CertificationExam exam = getExamById(id);
        if (exam != null) {
            exam.setTitle(examDetails.getTitle());
            exam.setDuration(examDetails.getDuration());
            exam.setCertification(examDetails.getCertification());
            return repository.save(exam);
        }
        return null;
    }

    public void deleteExam(Long id) {
        repository.deleteById(id);
    }
}
