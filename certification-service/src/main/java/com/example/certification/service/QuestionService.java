package com.example.certification.service;

import com.example.certification.Dto.QuestionRequest;
import com.example.certification.entity.Answer;
import com.example.certification.entity.CertificationExam;
import com.example.certification.entity.Question;
import com.example.certification.repository.AnswerRepository;
import com.example.certification.repository.CertificationExamRepository;
import com.example.certification.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CertificationExamRepository examRepository;
    private final AnswerRepository answerRepository;

    // Injection par constructeur
    public QuestionService(QuestionRepository questionRepository,
                           CertificationExamRepository examRepository,
                           AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
        this.answerRepository = answerRepository;
    }

    // Récupérer toutes les questions
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    // Récupérer une question par ID
    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElse(null);
    }

    // Créer une nouvelle question (simple)
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Mettre à jour une question existante
    public Question updateQuestion(Long id, Question details) {
        Question question = getQuestionById(id);
        if (question != null) {
            question.setContent(details.getContent());
            question.setType(details.getType());
            question.setExam(details.getExam());
            return questionRepository.save(question);
        }
        return null;
    }

    // Créer une question à partir d'un DTO (avec answers)
    @Transactional
    public Question createQuestionFromRequest(QuestionRequest request) {
        // 1. Vérifier que l'exam existe
        CertificationExam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam non trouvé avec l'ID: " + request.getExamId()));

        // 2. Créer la question
        Question question = new Question();
        question.setContent(request.getContent());
        question.setType(request.getType());
        question.setExam(exam);

        // 3. Sauvegarder d'abord la question
        Question savedQuestion = questionRepository.save(question);

        // 4. Créer et sauvegarder les réponses si elles existent
        if (request.getAnswers() != null && !request.getAnswers().isEmpty()) {
            List<Answer> answers = request.getAnswers().stream()
                    .map(a -> {
                        Answer answer = new Answer();
                        answer.setContent(a.getContent());
                        answer.setCorrect(a.isCorrect());
                        answer.setQuestion(savedQuestion);
                        return answer;
                    })
                    .collect(Collectors.toList());

            answerRepository.saveAll(answers);
            savedQuestion.setAnswers(answers);
        }

        return savedQuestion;
    }

    // Supprimer une question par ID
    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public Question save(Question question) {
        // Simple sauvegarde via le repository
        return questionRepository.save(question);
    }
}