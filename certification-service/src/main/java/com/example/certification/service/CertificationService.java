package com.example.certification.service;

import com.example.certification.entity.Certification;
import com.example.certification.entity.Question;
import com.example.certification.repository.CertificationRepository;
import com.example.certification.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final QuestionRepository questionRepository;

    public CertificationService(CertificationRepository certificationRepository,
                                QuestionRepository questionRepository) {
        this.certificationRepository = certificationRepository;
        this.questionRepository = questionRepository;
    }

    // CRUD Certification
    public List<Certification> getAllCertifications() {
        return certificationRepository.findAll();
    }

    public Optional<Certification> getCertificationById(Long id) {
        return certificationRepository.findById(id);
    }

    public Certification createCertification(Certification certification) {
        return certificationRepository.save(certification);
    }

    public Certification updateCertification(Long id, Certification certDetails) {
        return certificationRepository.findById(id).map(cert -> {
            cert.setTitle(certDetails.getTitle());
            cert.setLevel(certDetails.getLevel());
            cert.setDescription(certDetails.getDescription());
            cert.setPassingScore(certDetails.getPassingScore());
            return certificationRepository.save(cert);
        }).orElse(null);
    }

    public boolean deleteCertification(Long id) {
        if (certificationRepository.existsById(id)) {
            certificationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Récupérer toutes les questions liées à une certification via ses examens
    public List<Question> getQuestionsByCertification(Long certificationId) {
        return questionRepository.findByExam_Certification_Id(certificationId);
    }

}