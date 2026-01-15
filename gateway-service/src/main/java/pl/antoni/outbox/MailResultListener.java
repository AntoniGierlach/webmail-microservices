package pl.antoni.outbox;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.antoni.amqp.AmqpConfig;
import pl.antoni.outbox.dto.MailResultMessage;

import java.time.Instant;

@Component
public class MailResultListener {

    private final OutboxMailRepository repo;

    public MailResultListener(OutboxMailRepository repo) {
        this.repo = repo;
    }

    @RabbitListener(queues = AmqpConfig.MAIL_RESULT_QUEUE)
    @Transactional
    public void onResult(MailResultMessage msg) {
        if (msg.getOutboxId() == null) return;

        OutboxMail m = repo.findById(msg.getOutboxId()).orElse(null);
        if (m == null) return;

        String s = msg.getStatus();
        if ("SUCCESS".equalsIgnoreCase(s)) {
            m.setStatus(OutboxMailStatus.SENT)
                    .setSentAt(Instant.now())
                    .setLastError(null);
        } else {
            m.setStatus(OutboxMailStatus.FAILED)
                    .setAttempts(m.getAttempts() + 1)
                    .setLastError(trim(msg.getError()));
        }

        repo.save(m);
    }

    private String trim(String v) {
        if (v == null) return null;
        String t = v.trim();
        if (t.isEmpty()) return null;
        return t.length() > 500 ? t.substring(0, 500) : t;
    }
}
