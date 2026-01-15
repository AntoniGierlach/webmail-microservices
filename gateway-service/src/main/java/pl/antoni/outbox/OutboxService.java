package pl.antoni.outbox;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.antoni.amqp.AmqpConfig;
import pl.antoni.outbox.dto.MailSendMessage;
import pl.antoni.outbox.dto.SendMailRequest;

import java.util.List;

@Service
public class OutboxService {

    private final OutboxMailRepository repo;
    private final RabbitTemplate rabbit;

    public OutboxService(OutboxMailRepository repo, RabbitTemplate rabbit) {
        this.repo = repo;
        this.rabbit = rabbit;
    }

    @Transactional
    public OutboxMail enqueue(SendMailRequest req) {
        OutboxMail m = new OutboxMail()
                .setStatus(OutboxMailStatus.PENDING)
                .setToEmail(req.getTo())
                .setSubject(req.getSubject())
                .setBody(req.getBody())
                .setAttempts(0)
                .setLastError(null)
                .setSentAt(null);

        OutboxMail saved = repo.save(m);
        publish(saved);
        return saved;
    }

    @Transactional(readOnly = true)
    public List<OutboxMail> list() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public List<OutboxMail> listFailed() {
        return repo.findAllByStatus(OutboxMailStatus.FAILED);
    }

    @Transactional(readOnly = true)
    public OutboxMail get(Long id) {
        return repo.findById(id).orElseThrow(() -> new OutboxMailNotFoundException(id));
    }

    @Transactional
    public OutboxMail retry(Long id) {
        OutboxMail m = repo.findById(id).orElseThrow(() -> new OutboxMailNotFoundException(id));

        if (m.getStatus() == OutboxMailStatus.SENT) {
            return m;
        }

        m.setStatus(OutboxMailStatus.PENDING)
                .setLastError(null)
                .setSentAt(null);

        OutboxMail saved = repo.save(m);
        publish(saved);
        return saved;
    }

    @Transactional
    public int retryAllFailed() {
        List<OutboxMail> failed = repo.findAllByStatus(OutboxMailStatus.FAILED);
        int count = 0;
        for (OutboxMail m : failed) {
            m.setStatus(OutboxMailStatus.PENDING)
                    .setLastError(null)
                    .setSentAt(null);
            repo.save(m);
            publish(m);
            count++;
        }
        return count;
    }

    private void publish(OutboxMail m) {
        MailSendMessage msg = new MailSendMessage()
                .setOutboxId(m.getId())
                .setTo(m.getToEmail())
                .setSubject(m.getSubject())
                .setBody(m.getBody());

        rabbit.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.MAIL_SEND_KEY, msg);
    }
}
