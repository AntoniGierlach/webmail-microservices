package pl.antoni.mail;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.antoni.mail.dto.SendMailRequest;

@RestController
@RequestMapping("/api/mail")
public class MailController {

    private final MailSenderService service;

    public MailController(MailSenderService service) {
        this.service = service;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void send(@Valid @RequestBody SendMailRequest req) {
        service.send(req);
    }
}
