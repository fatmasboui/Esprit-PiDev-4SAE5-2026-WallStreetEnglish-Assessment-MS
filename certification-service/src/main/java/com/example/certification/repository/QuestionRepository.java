package com.example.certification.repository;

import com.example.certification.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
  // Cette méthode récupère toutes les questions d'une certification via ses exams
        List<Question> findByExam_Certification_Id(Long certificationId);
    }