package pl.antoni.gmail;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GmailComposePageController {

    @GetMapping("/gmail/compose")
    public String compose(@AuthenticationPrincipal OidcUser user, Model model) {
        model.addAttribute("email", user.getEmail());
        return "gmail-compose";
    }
}
