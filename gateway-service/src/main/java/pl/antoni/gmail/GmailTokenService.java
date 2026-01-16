package pl.antoni.gmail;

import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class GmailTokenService {

    private final GmailTokenRepository repo;

    public GmailTokenService(GmailTokenRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void upsert(String email, String subject, OAuth2AccessToken access, OAuth2RefreshToken refresh) {
        GmailToken t = repo.findByEmail(email).orElseGet(GmailToken::new);
        t.setEmail(email)
                .setSubject(subject)
                .setAccessToken(access.getTokenValue())
                .setExpiresAt(access.getExpiresAt());

        if (refresh != null) {
            t.setRefreshToken(refresh.getTokenValue());
        }

        repo.save(t);
    }

    @Transactional(readOnly = true)
    public GmailToken getByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    public static Instant safe(Instant v) {
        return v;
    }
}
