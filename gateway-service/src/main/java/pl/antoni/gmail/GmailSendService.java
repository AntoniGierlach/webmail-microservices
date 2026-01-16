package pl.antoni.gmail;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import pl.antoni.gmail.dto.GmailSendRequest;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class GmailSendService {

    private final GmailClientFactory factory;
    private final GmailOAuthTokenProvider tokens;

    public GmailSendService(GmailClientFactory factory, GmailOAuthTokenProvider tokens) {
        this.factory = factory;
        this.tokens = tokens;
    }

    public void send(OAuth2AuthenticationToken auth, GmailSendRequest req) {
        try {
            Gmail gmail = factory.create(tokens.accessToken(auth));
            String raw = buildRaw(req.getTo(), req.getSubject(), req.getBody());
            Message msg = new Message();
            msg.setRaw(Base64.getUrlEncoder().withoutPadding().encodeToString(raw.getBytes(StandardCharsets.UTF_8)));
            gmail.users().messages().send("me", msg).execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String buildRaw(String to, String subject, String body) {
        String safeTo = to == null ? "" : to.trim();
        String safeSubject = subject == null ? "" : subject.trim();
        String safeBody = body == null ? "" : body;

        String headers =
                "To: " + safeTo + "\r\n" +
                        "Subject: " + safeSubject + "\r\n" +
                        "MIME-Version: 1.0\r\n" +
                        "Content-Type: text/plain; charset=UTF-8\r\n" +
                        "Content-Transfer-Encoding: 8bit\r\n";

        return headers + "\r\n" + safeBody;
    }
}
