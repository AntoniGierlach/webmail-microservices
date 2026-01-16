package pl.antoni.gmail;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GmailTokenRepository extends JpaRepository<GmailToken, Long> {
    Optional<GmailToken> findByEmail(String email);
}
