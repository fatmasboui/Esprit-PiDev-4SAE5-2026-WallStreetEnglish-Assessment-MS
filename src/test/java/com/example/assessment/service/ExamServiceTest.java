package com.example.assessment.service;

import com.example.assessment.entity.Exam;
import com.example.assessment.repository.ExamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExamServiceTest {

    @Mock
    private ExamRepository repository;

    @InjectMocks
    private ExamService examService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetById() {
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Spring Boot Test");

        when(repository.findById(1L)).thenReturn(Optional.of(exam));

        Exam result = examService.getById(1L);

        assertNotNull(result);
        assertEquals("Spring Boot Test", result.getTitle());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testSave() {
        Exam exam = new Exam();
        exam.setTitle("New Exam");

        when(repository.save(any(Exam.class))).thenReturn(exam);

        Exam result = examService.save(exam);

        assertNotNull(result);
        assertEquals("New Exam", result.getTitle());
        verify(repository, times(1)).save(exam);
    }
}
