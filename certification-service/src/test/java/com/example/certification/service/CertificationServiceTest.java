package com.example.certification.service;

import com.example.certification.entity.Certification;
import com.example.certification.repository.CertificationRepository;
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
public class CertificationServiceTest {

    @Mock
    private CertificationRepository repository;

    @InjectMocks
    private CertificationService certificationService;

    private Certification cert;

    @BeforeEach
    void setUp() {
        cert = new Certification();
        cert.setId(1L);
        cert.setTitle("AWS Solutions Architect");
        cert.setProvider("Amazon");
    }

    @Test
    void testGetAllCertifications() {
        when(repository.findAll()).thenReturn(Arrays.asList(cert));
        List<Certification> results = certificationService.getAllCertifications();
        assertNotNull(results);
        assertEquals(1, results.size());
    }

    @Test
    void testGetCertificationById() {
        when(repository.findById(1L)).thenReturn(Optional.of(cert));
        Optional<Certification> result = certificationService.getCertificationById(1L);
        assertTrue(result.isPresent());
        assertEquals("AWS Solutions Architect", result.get().getTitle());
    }

    @Test
    void testSaveCertification() {
        when(repository.save(any(Certification.class))).thenReturn(cert);
        Certification result = certificationService.saveCertification(new Certification());
        assertNotNull(result);
        assertEquals("Amazon", result.getProvider());
    }

    @Test
    void testDeleteCertification() {
        doNothing().when(repository).deleteById(1L);
        certificationService.deleteCertification(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}
