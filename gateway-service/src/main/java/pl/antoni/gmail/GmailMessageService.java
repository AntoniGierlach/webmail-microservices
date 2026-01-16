package pl.antoni.gmail;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;
import com.google.api.services.gmail.model.MessagePartBody;
import com.google.api.services.gmail.model.MessagePartHeader;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import pl.antoni.gmail.dto.GmailMessageView;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class GmailMessageService {

    private final GmailClientFactory factory;
    private final GmailOAuthTokenProvider tokens;

    public GmailMessageService(GmailClientFactory factory, GmailOAuthTokenProvider tokens) {
        this.factory = factory;
        this.tokens = tokens;
    }

    public GmailMessageView get(OAuth2AuthenticationToken auth, String id) {
        try {
            Gmail gmail = factory.create(tokens.accessToken(auth));
            Message msg = gmail.users().messages().get("me", id).setFormat("full").execute();
            return map(msg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private GmailMessageView map(Message m) {
        String from = null;
        String to = null;
        String subject = null;
        Instant date = m.getInternalDate() == null ? null : Instant.ofEpochMilli(m.getInternalDate());

        if (m.getPayload() != null && m.getPayload().getHeaders() != null) {
            for (MessagePartHeader h : m.getPayload().getHeaders()) {
                if ("From".equalsIgnoreCase(h.getName())) from = h.getValue();
                if ("To".equalsIgnoreCase(h.getName())) to = h.getValue();
                if ("Subject".equalsIgnoreCase(h.getName())) subject = h.getValue();
            }
        }

        String text = findPart(m.getPayload(), "text/plain");
        String html = findPart(m.getPayload(), "text/html");

        String bodyText = text;
        if ((bodyText == null || bodyText.isBlank()) && html != null && !html.isBlank()) {
            bodyText = html.replaceAll("<[^>]+>", " ").replaceAll("\\s+", " ").trim();
        }

        return new GmailMessageView()
                .setId(m.getId())
                .setFrom(from)
                .setTo(to)
                .setSubject(subject)
                .setDate(date)
                .setBodyText(bodyText)
                .setBodyHtml(html);
    }

    private String findPart(MessagePart part, String mime) {
        if (part == null) return null;

        if (part.getMimeType() != null && part.getMimeType().toLowerCase().startsWith(mime)) {
            return decode(part.getBody());
        }

        if (part.getParts() != null) {
            for (MessagePart p : part.getParts()) {
                String v = findPart(p, mime);
                if (v != null && !v.isBlank()) return v;
            }
        }

        return null;
    }

    private String decode(MessagePartBody body) {
        if (body == null) return null;
        String data = body.getData();
        if (data == null || data.isBlank()) return null;
        byte[] bytes = Base64.getUrlDecoder().decode(data);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
