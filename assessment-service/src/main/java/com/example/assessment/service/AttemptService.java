package com.example.assessment.service;

import com.example.assessment.entity.Attempt;
import com.example.assessment.repository.AttemptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttemptService {

    private static final String ATTEMPT_NOT_FOUND = "Attempt not found with id: ";
    private final AttemptRepository repo;
    private final RestTemplate restTemplate;

    // CREATE
    public Attempt save(Attempt attempt) {
        log.info("Saving new attempt");
        return repo.save(attempt);
    }

    // UPDATE
    public Attempt update(Attempt attempt) {
        log.info("Updating attempt with id: {}", attempt.getId());
        if (!repo.existsById(attempt.getId())) {
            log.error(ATTEMPT_NOT_FOUND + "{}", attempt.getId());
            throw new IllegalArgumentException(ATTEMPT_NOT_FOUND + attempt.getId());
        }

        return repo.save(attempt);
    }

    // DELETE
    public void delete(Long id) {
        log.info("Deleting attempt with id: {}", id);
        if (!repo.existsById(id)) {
            log.error(ATTEMPT_NOT_FOUND + "{}", id);
            throw new IllegalArgumentException(ATTEMPT_NOT_FOUND + id);
        }
        repo.deleteById(id);
    }

    // READ ALL
    public List<Attempt> getAll() {
        log.info("Fetching all attempts");
        return repo.findAll();
    }

    // READ BY ID
    public Attempt getById(Long id) {
        log.info("Fetching attempt with id: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> {
                    log.error(ATTEMPT_NOT_FOUND + "{}", id);
                    return new IllegalArgumentException(ATTEMPT_NOT_FOUND + id);
                });
    }

    // READ BY USER ID
    public List<Attempt> getByUserId(Long userId) {
        log.info("Fetching attempts for user id: {}", userId);
        return repo.findByUserId(userId);
    }

    public Attempt submitExam(com.example.assessment.Dto.SubmitExamPayload payload, 
                              com.example.assessment.repository.ExamRepository examRepo,
                              com.example.assessment.repository.AnswerRepository answerRepo) {
        
        com.example.assessment.entity.Exam exam = examRepo.findById(payload.getExamId())
                .orElseThrow(() -> new IllegalArgumentException("Exam not found"));

        int totalQuestions = exam.getQuestions().size();
        if (totalQuestions == 0) totalQuestions = 1; // Prevent div by zero

        int correctAnswersCount = 0;

        for (com.example.assessment.Dto.SubmitAnswerDto sa : payload.getAnswers()) {
            if (sa.getAnswerId() == null || sa.getAnswerId() == 0) continue;
            
            // On cherche la bonne réponse pour la question donnée
            List<com.example.assessment.entity.Answer> dbAnswers = answerRepo.findByQuestionId(sa.getQuestionId());
            boolean isCorrect = dbAnswers.stream()
                .filter(a -> a.getId().equals(sa.getAnswerId()))
                .map(com.example.assessment.entity.Answer::isCorrect)
                .findFirst()
                .orElse(false);
            
            if (isCorrect) {
                correctAnswersCount++;
            }
        }

        int score = (correctAnswersCount * 100) / totalQuestions;
        int passingScore = exam.getPassingScore() != null ? exam.getPassingScore() : 50;
        boolean passed = score >= passingScore;

        Attempt attempt = new Attempt();
        attempt.setExam(exam);
        attempt.setUserId(payload.getUserId());
        attempt.setStudentName(payload.getStudentName());
        attempt.setScore(score);
        attempt.setPassed(passed);
        attempt.setDate(java.time.LocalDateTime.now());

        // --- APPEL A L'IA ---
        try {
            Map<String, Object> aiRequest = new HashMap<>();
            aiRequest.put("reading", score);
            aiRequest.put("listening", 70); // Valeurs par défaut à affiner
            aiRequest.put("speaking", 65);
            aiRequest.put("speed", 120);
            aiRequest.put("accuracy", 80);
            // Essayer d'abord l'adresse depuis Docker vers Windows, puis localhost en secours
            String aiUrl = "http://host.docker.internal:5000/predict";
            Map<String, Object> aiResponse = null;
            try {
                aiResponse = restTemplate.postForObject(aiUrl, aiRequest, Map.class);
            } catch (Exception ex) {
                log.warn("Échec sur host.docker.internal, tentative sur localhost...");
                aiUrl = "http://localhost:5000/predict";
                aiResponse = restTemplate.postForObject(aiUrl, aiRequest, Map.class);
            }
            
            if (aiResponse != null && aiResponse.containsKey("recommended_adaptive_score")) {
                Double recommendedScore = Double.valueOf(aiResponse.get("recommended_adaptive_score").toString());
                log.info("IA Recommendation Score: " + recommendedScore);
                attempt.setAiRecommendedScore(recommendedScore);
            }
        } catch (Exception e) {
            log.error("Échec total de l'appel à l'IA : " + e.getMessage());
        }

        return repo.save(attempt);
    }
}


