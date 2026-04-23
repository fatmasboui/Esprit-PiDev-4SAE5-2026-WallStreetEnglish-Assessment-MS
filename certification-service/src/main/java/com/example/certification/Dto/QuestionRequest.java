package com.example.certification.Dto;

import com.example.certification.entity.QuestionType;
import lombok.Data;
import java.util.List;

@Data
public class QuestionRequest {
    private String content;
    private QuestionType type;
    private Long examId;  // ← Au lieu de l'objet exam complet
    private List<AnswerRequest> answers;

    @Data
    public static class AnswerRequest {
        private String content;
        private boolean correct;
    }
}