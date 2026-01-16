package pl.antoni.gmail;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import pl.antoni.gmail.dto.GmailInboxMessage;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class GmailInboxService {

    private final GmailClientFactory factory;
    private final GmailOAuthTokenProvider tokens;

    public GmailInboxService(GmailClientFactory factory, GmailOAuthTokenProvider tokens) {
        this.factory = factory;
        this.tokens = tokens;
    }

    public List<GmailInboxMessage> inbox(OAuth2AuthenticationToken auth, long limit) {
        try {
            Gmail gmail = factory.create(tokens.accessToken(auth));

            ListMessagesResponse resp = gmail.users()
                    .messages()
                    .list("me")
                    .setLabelIds(List.of("INBOX"))
                    .setMaxResults(limit)
                    .execute();

            List<Message> msgs = resp.getMessages();
            List<GmailInboxMessage> out = new ArrayList<>();
            if (msgs == null) return out;

            for (Message m : msgs) {
                Message meta = gmail.users()
                        .messages()
                        .get("me", m.getId())
                        .setFormat("metadata")
                        .setMetadataHeaders(List.of("From", "Subject", "Date"))
                        .execute();

                out.add(map(meta));
            }

            return out;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private GmailInboxMessage map(Message m) {
        String from = null;
        String subject = null;
        Instant date = m.getInternalDate() == null ? null : Instant.ofEpochMilli(m.getInternalDate());

        if (m.getPayload() != null && m.getPayload().getHeaders() != null) {
            for (MessagePartHeader h : m.getPayload().getHeaders()) {
                if ("From".equalsIgnoreCase(h.getName())) from = h.getValue();
                if ("Subject".equalsIgnoreCase(h.getName())) subject = h.getValue();
            }
        }

        return new GmailInboxMessage()
                .setId(m.getId())
                .setFrom(from)
                .setSubject(subject)
                .setDate(date);
    }
}
