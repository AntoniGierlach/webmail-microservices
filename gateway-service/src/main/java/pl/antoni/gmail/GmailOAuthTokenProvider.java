package pl.antoni.gmail;

import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class GmailOAuthTokenProvider {

    private final OAuth2AuthorizedClientManager manager;

    public GmailOAuthTokenProvider(OAuth2AuthorizedClientManager manager) {
        this.manager = manager;
    }

    public String accessToken(OAuth2AuthenticationToken auth) {
        OAuth2AuthorizeRequest req = OAuth2AuthorizeRequest.withClientRegistrationId("google")
                .principal(auth)
                .build();

        OAuth2AuthorizedClient client = manager.authorize(req);
        if (client == null || client.getAccessToken() == null) {
            throw new org.springframework.security.oauth2.client.ClientAuthorizationRequiredException("google");
        }

        return client.getAccessToken().getTokenValue();
    }
}
