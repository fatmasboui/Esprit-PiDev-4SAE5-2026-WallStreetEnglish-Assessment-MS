package com.example.assessment.service;

import com.example.assessment.entity.Exam;
import com.example.assessment.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExamService {

    @Autowired
    private ExamRepository repository;

    public List<Exam> getAll() {
        return repository.findAll();
    }

    public Exam save(Exam exam) {
        return repository.save(exam);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Exam getById(Long id) {
        Optional<Exam> exam = repository.findById(id);
        return exam.orElse(null);
    }

    // Méthode avec exception
    public Exam getByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));
    }

    // Mise à jour CORRIGÉE
    @Transactional
    public Exam update(Long id, Exam examDetails) {
        Exam existingExam = getByIdOrThrow(id);

        // Mettre à jour les champs simples
        existingExam.setTitle(examDetails.getTitle());
        existingExam.setDescription(examDetails.getDescription());
        existingExam.setDuration(examDetails.getDuration());
        existingExam.setExamType(examDetails.getExamType());

        // Gestion spéciale pour la collection questions
        if (examDetails.getQuestions() != null) {
            // Ne pas remplacer la collection, mais la synchroniser
            existingExam.getQuestions().clear();
            existingExam.getQuestions().addAll(examDetails.getQuestions());

            // Mettre à jour la relation inverse
            for (var question : existingExam.getQuestions()) {
                question.setExam(existingExam);
            }
        }

        return repository.save(existingExam);
    }

    // Version alternative plus sûre pour la mise à jour
    @Transactional
    public Exam updateSafe(Long id, Exam examDetails) {
        Exam existingExam = getByIdOrThrow(id);

        // Mettre à jour uniquement les champs simples
        existingExam.setTitle(examDetails.getTitle());
        existingExam.setDescription(examDetails.getDescription());
        existingExam.setDuration(examDetails.getDuration());
        existingExam.setExamType(examDetails.getExamType());

        // NE PAS mettre à jour la collection questions ici
        // La gestion des questions se fait via des endpoints séparés

        return repository.save(existingExam);
    }
}