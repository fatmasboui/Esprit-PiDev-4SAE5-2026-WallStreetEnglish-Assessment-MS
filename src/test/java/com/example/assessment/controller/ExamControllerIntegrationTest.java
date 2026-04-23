package com.example.assessment.controller;

import com.example.assessment.entity.Exam;
import com.example.assessment.service.ExamService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
    controllers = ExamController.class,
    excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
        org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration.class
    }
)
class ExamControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExamService examService;

    @Test
    void testGetAllExams() throws Exception {
        Exam exam = new Exam();
        exam.setId(1L);
        exam.setTitle("Certified Java Developer");

        given(examService.getAll()).willReturn(Arrays.asList(exam));

        mockMvc.perform(get("/exams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Certified Java Developer"));
    }

    @Test
    void testGetAllExamsEmpty() throws Exception {
        given(examService.getAll()).willReturn(Arrays.asList());

        mockMvc.perform(get("/exams")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
