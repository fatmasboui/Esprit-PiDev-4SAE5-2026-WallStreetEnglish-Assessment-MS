package com.example.certification.service;

import com.example.certification.entity.Certification;
import com.example.certification.repository.CertificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CertificationServiceTest {

    @Mock
    private CertificationRepository repository;

    @InjectMocks
    private CertificationService certificationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCertificationById() {
        Certification cert = new Certification();
        cert.setId(1L);
        cert.setTitle("AWS Solutions Architect");

        when(repository.findById(1L)).thenReturn(Optional.of(cert));

        Optional<Certification> result = certificationService.getCertificationById(1L);

        assertTrue(result.isPresent());
        assertEquals("AWS Solutions Architect", result.get().getTitle());
    }
}
