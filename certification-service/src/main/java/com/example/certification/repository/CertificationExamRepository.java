package com.example.certification.repository;

import com.example.certification.entity.CertificationExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CertificationExamRepository extends JpaRepository<CertificationExam, Long> {
    List<CertificationExam> findByCertificationId(Long certificationId);
}
