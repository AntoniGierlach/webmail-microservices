package pl.antoni.mail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import pl.antoni.mail.dto.SendMailRequest;

@Service
public class MailSenderService {

    private final JavaMailSender sender;
    private final String from;

    public MailSenderService(JavaMailSender sender, @Value("${MAIL_FROM:no-reply@mailpilot.local}") String from) {
        this.sender = sender;
        this.from = from;
    }

    public void send(SendMailRequest req) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(req.getTo());
        msg.setSubject(req.getSubject());
        msg.setText(req.getBody());
        sender.send(msg);
    }
}
