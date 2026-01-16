package pl.antoni.gmail.dto;

public class GmailSendRequest {
    private String to;
    private String subject;
    private String body;

    public String getTo() {
        return to;
    }

    public GmailSendRequest setTo(String to) {
        this.to = to;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public GmailSendRequest setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public GmailSendRequest setBody(String body) {
        this.body = body;
        return this;
    }
}
