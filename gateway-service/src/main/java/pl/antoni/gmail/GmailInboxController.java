package pl.antoni.gmail;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.antoni.gmail.dto.GmailInboxMessage;

import java.util.List;

@RestController
public class GmailInboxController {

    private final GmailInboxService inbox;

    public GmailInboxController(GmailInboxService inbox) {
        this.inbox = inbox;
    }

    @GetMapping("/api/gmail/inbox")
    public List<GmailInboxMessage> inbox(OAuth2AuthenticationToken auth,
                                         @RequestParam(name = "limit", defaultValue = "20") long limit) {
        long safe = Math.max(1, Math.min(100, limit));
        return inbox.inbox(auth, safe);
    }
}
