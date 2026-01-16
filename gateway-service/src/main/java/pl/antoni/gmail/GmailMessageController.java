package pl.antoni.gmail;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.antoni.gmail.dto.GmailMessageView;

@RestController
public class GmailMessageController {

    private final GmailMessageService messages;

    public GmailMessageController(GmailMessageService messages) {
        this.messages = messages;
    }

    @GetMapping("/api/gmail/messages/{id}")
    public GmailMessageView message(OAuth2AuthenticationToken auth, @PathVariable("id") String id) {
        return messages.get(auth, id);
    }

}
