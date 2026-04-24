package com.example.certification.service;

import com.example.certification.entity.Certification;
import com.example.certification.repository.CertificationRepository;
import com.example.certification.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CertificationServiceTest {

    @Mock
    private CertificationRepository certificationRepository;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private CertificationService certificationService;

    private Certification cert;

    @BeforeEach
    void setUp() {
        cert = new Certification();
        cert.setId(1L);
        cert.setTitle("AWS Solutions Architect");
        cert.setLevel("Advanced");
        cert.setDescription("AWS certification");
        cert.setPassingScore(70);
    }

    @Test
    void testGetAllCertifications() {
        when(certificationRepository.findAll()).thenReturn(Arrays.asList(cert));
        List<Certification> results = certificationService.getAllCertifications();
        assertNotNull(results);
        assertEquals(1, results.size());
        verify(certificationRepository, times(1)).findAll();
    }

    @Test
    void testGetCertificationById_Found() {
        when(certificationRepository.findById(1L)).thenReturn(Optional.of(cert));
        Optional<Certification> result = certificationService.getCertificationById(1L);
        assertTrue(result.isPresent());
        assertEquals("AWS Solutions Architect", result.get().getTitle());
    }

    @Test
    void testGetCertificationById_NotFound() {
        when(certificationRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<Certification> result = certificationService.getCertificationById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testSaveCertification() {
        when(certificationRepository.save(any(Certification.class))).thenReturn(cert);
        Certification result = certificationService.saveCertification(new Certification());
        assertNotNull(result);
        assertEquals("AWS Solutions Architect", result.getTitle());
    }

    @Test
    void testDeleteCertification_Exists() {
        when(certificationRepository.existsById(1L)).thenReturn(true);
        doNothing().when(certificationRepository).deleteById(1L);
        boolean deleted = certificationService.deleteCertification(1L);
        assertTrue(deleted);
        verify(certificationRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCertification_NotExists() {
        when(certificationRepository.existsById(99L)).thenReturn(false);
        boolean deleted = certificationService.deleteCertification(99L);
        assertFalse(deleted);
        verify(certificationRepository, never()).deleteById(any());
    }

    @Test
    void testUpdateCertification_Success() {
        Certification updated = new Certification();
        updated.setTitle("Azure Expert");
        updated.setLevel("Expert");
        updated.setDescription("Azure certification");
        updated.setPassingScore(80);

        when(certificationRepository.findById(1L)).thenReturn(Optional.of(cert));
        when(certificationRepository.save(any(Certification.class))).thenReturn(cert);

        Certification result = certificationService.updateCertification(1L, updated);
        assertNotNull(result);
    }
}
