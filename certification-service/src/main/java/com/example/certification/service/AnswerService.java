package com.example.certification.service;

import com.example.certification.entity.Answer;
import com.example.certification.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository repository;

    public List<Answer> getAllAnswers() {
        return repository.findAll();
    }

    public Answer getAnswerById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public Answer saveAnswer(Answer answer) {
        return repository.save(answer);
    }

    public Answer updateAnswer(Long id, Answer details) {
        Answer answer = getAnswerById(id);
        if (answer != null) {
            answer.setContent(details.getContent());
            answer.setCorrect(details.isCorrect());
            answer.setQuestion(details.getQuestion());
            return repository.save(answer);
        }
        return null;
    }

    public void deleteAnswer(Long id) {
        repository.deleteById(id);
    }
}
