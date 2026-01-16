package pl.antoni.security;

import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthDebugController {

    private final OAuth2AuthorizedClientService clients;

    public OAuthDebugController(OAuth2AuthorizedClientService clients) {
        this.clients = clients;
    }

    @GetMapping("/api/oauth/debug")
    public Debug debug(OAuth2AuthenticationToken auth) {
        OAuth2AuthorizedClient c = clients.loadAuthorizedClient("google", auth.getName());
        return new Debug()
                .setPrincipal(auth.getName())
                .setHasClient(c != null)
                .setHasAccessToken(c != null && c.getAccessToken() != null)
                .setHasRefreshToken(c != null && c.getRefreshToken() != null)
                .setAccessTokenExpiresAt(c == null || c.getAccessToken() == null ? null : c.getAccessToken().getExpiresAt());
    }

    public static class Debug {
        private String principal;
        private boolean hasClient;
        private boolean hasAccessToken;
        private boolean hasRefreshToken;
        private java.time.Instant accessTokenExpiresAt;

        public String getPrincipal() {
            return principal;
        }

        public Debug setPrincipal(String principal) {
            this.principal = principal;
            return this;
        }

        public boolean isHasClient() {
            return hasClient;
        }

        public Debug setHasClient(boolean hasClient) {
            this.hasClient = hasClient;
            return this;
        }

        public boolean isHasAccessToken() {
            return hasAccessToken;
        }

        public Debug setHasAccessToken(boolean hasAccessToken) {
            this.hasAccessToken = hasAccessToken;
            return this;
        }

        public boolean isHasRefreshToken() {
            return hasRefreshToken;
        }

        public Debug setHasRefreshToken(boolean hasRefreshToken) {
            this.hasRefreshToken = hasRefreshToken;
            return this;
        }

        public java.time.Instant getAccessTokenExpiresAt() {
            return accessTokenExpiresAt;
        }

        public Debug setAccessTokenExpiresAt(java.time.Instant accessTokenExpiresAt) {
            this.accessTokenExpiresAt = accessTokenExpiresAt;
            return this;
        }
    }
}
