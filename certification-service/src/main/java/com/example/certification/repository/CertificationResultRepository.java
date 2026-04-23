package com.example.certification.repository;

import com.example.certification.entity.CertificationResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CertificationResultRepository extends JpaRepository<CertificationResult, Long> {
    List<CertificationResult> findByUserId(Long userId);
}
