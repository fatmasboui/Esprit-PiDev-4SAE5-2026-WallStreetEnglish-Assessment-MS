import com.example.assessment.entity.Attempt;
import com.example.assessment.repository.AttemptRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttemptService {

    private static final String ATTEMPT_NOT_FOUND = "Attempt not found with id: ";
    private final AttemptRepository repo;

    // CREATE
    public Attempt save(Attempt attempt) {
        log.info("Saving new attempt");
        return repo.save(attempt);
    }

    // UPDATE
    public Attempt update(Attempt attempt) {
        log.info("Updating attempt with id: {}", attempt.getId());
        if (!repo.existsById(attempt.getId())) {
            log.error(ATTEMPT_NOT_FOUND + "{}", attempt.getId());
            throw new IllegalArgumentException(ATTEMPT_NOT_FOUND + attempt.getId());
        }

        return repo.save(attempt);
    }

    // DELETE
    public void delete(Long id) {
        log.info("Deleting attempt with id: {}", id);
        if (!repo.existsById(id)) {
            log.error(ATTEMPT_NOT_FOUND + "{}", id);
            throw new IllegalArgumentException(ATTEMPT_NOT_FOUND + id);
        }
        repo.deleteById(id);
    }

    // READ ALL
    public List<Attempt> getAll() {
        log.info("Fetching all attempts");
        return repo.findAll();
    }

    // READ BY ID
    public Attempt getById(Long id) {
        log.info("Fetching attempt with id: {}", id);
        return repo.findById(id)
                .orElseThrow(() -> {
                    log.error(ATTEMPT_NOT_FOUND + "{}", id);
                    return new IllegalArgumentException(ATTEMPT_NOT_FOUND + id);
                });
    }
}

