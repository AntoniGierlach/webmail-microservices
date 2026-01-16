package pl.antoni.outbox;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.antoni.outbox.dto.SendMailRequest;

import java.util.List;

@RestController
@RequestMapping("/api/outbox")
public class OutboxController {

    private final OutboxService service;

    public OutboxController(OutboxService service) {
        this.service = service;
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OutboxMail send(@Valid @RequestBody SendMailRequest req) {
        return service.enqueue(req);
    }

    @GetMapping("/mails")
    public List<OutboxMail> mails() {
        return service.list();
    }

    @GetMapping("/mails/failed")
    public List<OutboxMail> failed() {
        return service.listFailed();
    }

    @GetMapping("/mails/{id}")
    public OutboxMail mail(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PostMapping("/mails/{id}/retry")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OutboxMail retry(@PathVariable("id") Long id) {
        return service.retry(id);
    }

    @PostMapping("/mails/retry-failed")
    public RetryAllResponse retryFailed() {
        return new RetryAllResponse().setRetried(service.retryAllFailed());
    }

    public static class RetryAllResponse {
        private int retried;

        public int getRetried() {
            return retried;
        }

        public RetryAllResponse setRetried(int retried) {
            this.retried = retried;
            return this;
        }
    }
}
