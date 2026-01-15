package pl.antoni.outbox;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.antoni.outbox.dto.SendMailRequest;

@RestController
@RequestMapping("/api/outbox")
public class OutboxController {

    private final OutboxService service;

    public OutboxController(OutboxService service) {
        this.service = service;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void send(@Valid @RequestBody SendMailRequest req) {
        service.send(req);
    }
}
