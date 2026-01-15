package pl.antoni.outbox;

import org.springframework.stereotype.Service;
import pl.antoni.outbox.dto.SendMailRequest;

@Service
public class OutboxService {

    private final MailServiceClient client;

    public OutboxService(MailServiceClient client) {
        this.client = client;
    }

    public void send(SendMailRequest req) {
        client.send(req);
    }
}
