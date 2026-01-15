package pl.antoni.outbox;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import pl.antoni.outbox.dto.SendMailRequest;

@Component
public class MailServiceClient {

    private final WebClient webClient;

    public MailServiceClient(@Value("${services.mail.base-url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public void send(SendMailRequest req) {
        try {
            webClient.post()
                    .uri("/api/mail/send")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(req)
                    .retrieve()
                    .toBodilessEntity()
                    .block();
        } catch (Exception e) {
            throw new MailServiceUnavailableException("Mail service call failed");
        }
    }

}
