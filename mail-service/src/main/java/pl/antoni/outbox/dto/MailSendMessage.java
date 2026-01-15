package pl.antoni.outbox.dto;

public class MailSendMessage {
    private Long outboxId;
    private String to;
    private String subject;
    private String body;

    public Long getOutboxId() {
        return outboxId;
    }

    public MailSendMessage setOutboxId(Long outboxId) {
        this.outboxId = outboxId;
        return this;
    }

    public String getTo() {
        return to;
    }

    public MailSendMessage setTo(String to) {
        this.to = to;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MailSendMessage setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public MailSendMessage setBody(String body) {
        this.body = body;
        return this;
    }
}
