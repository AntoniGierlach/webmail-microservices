package pl.antoni.outbox;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pl.antoni.amqp.AmqpConfig;
import pl.antoni.mail.MailSenderService;
import pl.antoni.mail.dto.SendMailRequest;
import pl.antoni.outbox.dto.MailResultMessage;
import pl.antoni.outbox.dto.MailSendMessage;

@Component
public class MailQueueWorker {

    private final MailSenderService sender;
    private final RabbitTemplate rabbit;

    public MailQueueWorker(MailSenderService sender, RabbitTemplate rabbit) {
        this.sender = sender;
        this.rabbit = rabbit;
    }

    @RabbitListener(queues = AmqpConfig.MAIL_SEND_QUEUE)
    public void onSend(MailSendMessage msg) {
        MailResultMessage result = new MailResultMessage().setOutboxId(msg.getOutboxId());

        try {
            SendMailRequest req = new SendMailRequest()
                    .setTo(msg.getTo())
                    .setSubject(msg.getSubject())
                    .setBody(msg.getBody());

            sender.send(req);

            result.setStatus("SUCCESS").setError(null);
        } catch (Exception e) {
            String err = e.getClass().getSimpleName();
            if (e.getMessage() != null && !e.getMessage().isBlank()) {
                err = err + ": " + e.getMessage();
            }
            result.setStatus("FAILED").setError(err);
        }

        rabbit.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.MAIL_RESULT_KEY, result);
    }
}
