package pl.antoni.outbox;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OutboxMailRepository extends JpaRepository<OutboxMail, Long> {
    List<OutboxMail> findAllByStatus(OutboxMailStatus status);
}