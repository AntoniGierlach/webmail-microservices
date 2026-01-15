package pl.antoni.outbox.ui;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.antoni.outbox.OutboxMail;
import pl.antoni.outbox.OutboxService;
import pl.antoni.outbox.dto.SendMailRequest;

import java.util.Comparator;
import java.util.List;

@Controller
public class OutboxPageController {

    private final OutboxService service;

    public OutboxPageController(OutboxService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/outbox";
    }

    @GetMapping("/outbox")
    public String page(Model model, @ModelAttribute("form") OutboxSendForm form) {
        List<OutboxMail> mails = service.list().stream()
                .sorted(Comparator.comparing(OutboxMail::getId).reversed())
                .toList();

        model.addAttribute("mails", mails);
        return "outbox";
    }

    @PostMapping("/outbox/send")
    public String send(@Valid @ModelAttribute("form") OutboxSendForm form, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("mails", service.list().stream()
                    .sorted(Comparator.comparing(OutboxMail::getId).reversed())
                    .toList());
            return "outbox";
        }

        service.enqueue(new SendMailRequest()
                .setTo(form.getTo())
                .setSubject(form.getSubject())
                .setBody(form.getBody()));

        return "redirect:/outbox";
    }

    @PostMapping("/outbox/retry/{id}")
    public String retry(@PathVariable Long id) {
        service.retry(id);
        return "redirect:/outbox";
    }

    @PostMapping("/outbox/retry-failed")
    public String retryFailed() {
        service.retryAllFailed();
        return "redirect:/outbox";
    }
}
