package com.example.certification.service;

import com.example.certification.Dto.QuestionRequest;
import com.example.certification.entity.Answer;
import com.example.certification.entity.CertificationExam;
import com.example.certification.entity.Question;
import com.example.certification.repository.AnswerRepository;
import com.example.certification.repository.CertificationExamRepository;
import com.example.certification.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final CertificationExamRepository examRepository;
    private final AnswerRepository answerRepository;

    public List<Question> getAllQuestions() {
        log.info("Fetching all questions");
        return questionRepository.findAll();
    }

    public Question getQuestionById(Long id) {
        log.info("Fetching question with id: {}", id);
        return questionRepository.findById(id).orElse(null);
    }

    public Question saveQuestion(Question question) {
        log.info("Saving new question");
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long id, Question details) {
        log.info("Updating question with id: {}", id);
        return questionRepository.findById(id).map(question -> {
            question.setContent(details.getContent());
            question.setType(details.getType());
            question.setExam(details.getExam());
            return questionRepository.save(question);
        }).orElse(null);
    }

    @Transactional
    public Question createQuestionFromRequest(QuestionRequest request) {
        log.info("Creating question from request for exam id: {}", request.getExamId());
        CertificationExam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam non trouvé avec l'ID: " + request.getExamId()));

        Question question = new Question();
        question.setContent(request.getContent());
        question.setType(request.getType());
        question.setExam(exam);

        Question savedQuestion = questionRepository.save(question);

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

    public void deleteQuestion(Long id) {
        log.info("Deleting question with id: {}", id);
        questionRepository.deleteById(id);
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }
}