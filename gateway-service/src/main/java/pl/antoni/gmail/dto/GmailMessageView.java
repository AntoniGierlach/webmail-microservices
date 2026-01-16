package pl.antoni.gmail.dto;

import java.time.Instant;

public class GmailMessageView {
    private String id;
    private String from;
    private String to;
    private String subject;
    private Instant date;
    private String bodyText;
    private String bodyHtml;

    public String getId() {
        return id;
    }

    public GmailMessageView setId(String id) {
        this.id = id;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public GmailMessageView setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getTo() {
        return to;
    }

    public GmailMessageView setTo(String to) {
        this.to = to;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public GmailMessageView setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Instant getDate() {
        return date;
    }

    public GmailMessageView setDate(Instant date) {
        this.date = date;
        return this;
    }

    public String getBodyText() {
        return bodyText;
    }

    public GmailMessageView setBodyText(String bodyText) {
        this.bodyText = bodyText;
        return this;
    }

    public String getBodyHtml() {
        return bodyHtml;
    }

    public GmailMessageView setBodyHtml(String bodyHtml) {
        this.bodyHtml = bodyHtml;
        return this;
    }
}
