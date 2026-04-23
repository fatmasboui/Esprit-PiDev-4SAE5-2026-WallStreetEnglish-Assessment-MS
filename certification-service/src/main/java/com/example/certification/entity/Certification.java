package com.example.certification.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private Level level;

    private String description;
    private double passingScore;

    @OneToMany(mappedBy = "certification", cascade = CascadeType.ALL)

    private List<CertificationExam> exams;
}