package pl.antoni.gmail;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GmailMeController {

    private final GmailTokenRepository repo;

    public GmailMeController(GmailTokenRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/api/gmail/me")
    public Me me(@AuthenticationPrincipal OidcUser user) {
        GmailToken t = repo.findByEmail(user.getEmail()).orElse(null);
        return new Me()
                .setEmail(user.getEmail())
                .setHasToken(t != null)
                .setExpiresAt(t == null ? null : t.getExpiresAt());
    }

    public static class Me {
        private String email;
        private boolean hasToken;
        private java.time.Instant expiresAt;

        public String getEmail() {
            return email;
        }

        public Me setEmail(String email) {
            this.email = email;
            return this;
        }

        public boolean isHasToken() {
            return hasToken;
        }

        public Me setHasToken(boolean hasToken) {
            this.hasToken = hasToken;
            return this;
        }

        public java.time.Instant getExpiresAt() {
            return expiresAt;
        }

        public Me setExpiresAt(java.time.Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }
    }
}
