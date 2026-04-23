import com.example.certification.entity.Certification;
import com.example.certification.entity.Question;
import com.example.certification.repository.CertificationRepository;
import com.example.certification.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CertificationService {

    private final CertificationRepository certificationRepository;
    private final QuestionRepository questionRepository;

    // CRUD Certification
    public List<Certification> getAllCertifications() {
        log.info("Fetching all certifications");
        return certificationRepository.findAll();
    }

    public Optional<Certification> getCertificationById(Long id) {
        log.info("Fetching certification with id: {}", id);
        return certificationRepository.findById(id);
    }

    public Certification saveCertification(Certification certification) {
        log.info("Saving new certification: {}", certification.getTitle());
        return certificationRepository.save(certification);
    }

    public Certification updateCertification(Long id, Certification certDetails) {
        log.info("Updating certification with id: {}", id);
        return certificationRepository.findById(id).map(cert -> {
            cert.setTitle(certDetails.getTitle());
            cert.setLevel(certDetails.getLevel());
            cert.setDescription(certDetails.getDescription());
            cert.setPassingScore(certDetails.getPassingScore());
            return certificationRepository.save(cert);
        }).orElseGet(() -> {
            log.warn("Certification not found with id: {}", id);
            return null;
        });
    }

    public boolean deleteCertification(Long id) {
        log.info("Deleting certification with id: {}", id);
        if (certificationRepository.existsById(id)) {
            certificationRepository.deleteById(id);
            return true;
        }
        log.warn("Certification not found for deletion with id: {}", id);
        return false;
    }

    // Récupérer toutes les questions liées à une certification via ses examens
    public List<Question> getQuestionsByCertification(Long certificationId) {
        log.info("Fetching questions for certification id: {}", certificationId);
        return questionRepository.findByExam_Certification_Id(certificationId);
    }
}