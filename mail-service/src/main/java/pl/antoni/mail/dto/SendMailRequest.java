package pl.antoni.mail.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SendMailRequest {

    @Email
    @NotBlank
    @Size(max = 254)
    private String to;

    @NotBlank
    @Size(max = 150)
    private String subject;

    @NotBlank
    @Size(max = 20000)
    private String body;

    public String getTo() {
        return to;
    }

    public SendMailRequest setTo(String to) {
        this.to = to;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public SendMailRequest setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public SendMailRequest setBody(String body) {
        this.body = body;
        return this;
    }
}
