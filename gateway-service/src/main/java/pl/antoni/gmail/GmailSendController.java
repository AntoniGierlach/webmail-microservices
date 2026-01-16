package pl.antoni.gmail;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.antoni.gmail.dto.GmailSendRequest;

@RestController
public class GmailSendController {

    private final GmailSendService sender;

    public GmailSendController(GmailSendService sender) {
        this.sender = sender;
    }

    @PostMapping("/api/gmail/send")
    public ResponseEntity<Void> send(OAuth2AuthenticationToken auth, @RequestBody GmailSendRequest req) {
        sender.send(auth, req);
        return ResponseEntity.accepted().build();
    }
}
