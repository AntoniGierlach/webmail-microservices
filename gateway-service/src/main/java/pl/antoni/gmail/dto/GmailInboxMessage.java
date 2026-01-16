package pl.antoni.gmail.dto;

import java.time.Instant;

public class GmailInboxMessage {

    private String id;
    private String from;
    private String subject;
    private Instant date;

    public String getId() {
        return id;
    }

    public GmailInboxMessage setId(String id) {
        this.id = id;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public GmailInboxMessage setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public GmailInboxMessage setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public Instant getDate() {
        return date;
    }

    public GmailInboxMessage setDate(Instant date) {
        this.date = date;
        return this;
    }
}
