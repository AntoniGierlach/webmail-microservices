package pl.antoni.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import pl.antoni.gmail.GmailTokenService;

import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final OAuth2AuthorizedClientService clients;
    private final GmailTokenService tokens;

    public OAuth2LoginSuccessHandler(OAuth2AuthorizedClientService clients, GmailTokenService tokens) {
        this.clients = clients;
        this.tokens = tokens;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        if (authentication instanceof OAuth2AuthenticationToken tok) {
            if ("google".equals(tok.getAuthorizedClientRegistrationId()) && tok.getPrincipal() instanceof OidcUser user) {
                OAuth2AuthorizedClient client = clients.loadAuthorizedClient("google", tok.getName());
                if (client != null) {
                    tokens.upsert(user.getEmail(), user.getSubject(), client.getAccessToken(), client.getRefreshToken());
                }
            }
        }

        response.sendRedirect("/gmail/inbox");
    }
}
