package com.example.certification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.certification.entity.Certificate;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    // Ici tu peux ajouter des requêtes personnalisées si besoin
}
