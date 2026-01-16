package pl.antoni.gmail.ui;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.antoni.gmail.GmailInboxService;
import pl.antoni.gmail.GmailMessageService;
import pl.antoni.gmail.GmailToken;
import pl.antoni.gmail.GmailTokenRepository;

import java.util.Comparator;

@Controller
public class GmailPageController {

    private final GmailTokenRepository repo;
    private final GmailInboxService inbox;
    private final GmailMessageService messages;

    public GmailPageController(GmailTokenRepository repo, GmailInboxService inbox, GmailMessageService messages) {
        this.repo = repo;
        this.inbox = inbox;
        this.messages = messages;
    }

    @GetMapping("/gmail/inbox")
    public String inbox(org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken auth,
                        @AuthenticationPrincipal OidcUser user,
                        @RequestParam(name = "limit", defaultValue = "20") long limit,
                        Model model) {
        var list = inbox.inbox(auth, Math.max(1, Math.min(100, limit))).stream()
                .sorted(java.util.Comparator.comparing((pl.antoni.gmail.dto.GmailInboxMessage m) ->
                        m.getDate() == null ? java.time.Instant.EPOCH : m.getDate()
                ).reversed())
                .toList();

        model.addAttribute("email", user.getEmail());
        model.addAttribute("mails", list);
        model.addAttribute("limit", limit);
        model.addAttribute("selected", null);
        return "gmail-inbox";
    }


    @GetMapping("/gmail/inbox/{id}")
    public String view(org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken auth,
                       @AuthenticationPrincipal OidcUser user,
                       @RequestParam(name = "limit", defaultValue = "20") long limit,
                       Model model) {
        GmailToken token = repo.findByEmail(user.getEmail()).orElseThrow(() -> new pl.antoni.gmail.GmailTokenMissingException(user.getEmail()));
        var list = inbox.inbox(auth, Math.max(1, Math.min(100, limit))).stream()
                .sorted(java.util.Comparator.comparing((pl.antoni.gmail.dto.GmailInboxMessage m) ->
                        m.getDate() == null ? java.time.Instant.EPOCH : m.getDate()
                ).reversed())
                .toList();


        model.addAttribute("email", user.getEmail());
        model.addAttribute("mails", list);
        model.addAttribute("limit", limit);
        model.addAttribute("selected", null);
        return "gmail-inbox";
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(pl.antoni.gmail.GmailTokenMissingException.class)
    public String handleMissingToken() {
        return "redirect:/oauth2/authorization/google";
    }
}
