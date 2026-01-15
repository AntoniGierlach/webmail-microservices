package pl.antoni.outbox;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "outbox_mails")
public class OutboxMail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OutboxMailStatus status;

    @Column(nullable = false, length = 254)
    private String toEmail;

    @Column(nullable = false, length = 150)
    private String subject;

    @Column(nullable = false, columnDefinition = "text")
    private String body;

    @Column(nullable = false)
    private int attempts;

    @Column(length = 500)
    private String lastError;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant sentAt;

    @PrePersist
    void onCreate() {
        this.createdAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public OutboxMailStatus getStatus() {
        return status;
    }

    public OutboxMail setStatus(OutboxMailStatus status) {
        this.status = status;
        return this;
    }

    public String getToEmail() {
        return toEmail;
    }

    public OutboxMail setToEmail(String toEmail) {
        this.toEmail = toEmail;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public OutboxMail setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getBody() {
        return body;
    }

    public OutboxMail setBody(String body) {
        this.body = body;
        return this;
    }

    public int getAttempts() {
        return attempts;
    }

    public OutboxMail setAttempts(int attempts) {
        this.attempts = attempts;
        return this;
    }

    public String getLastError() {
        return lastError;
    }

    public OutboxMail setLastError(String lastError) {
        this.lastError = lastError;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public OutboxMail setSentAt(Instant sentAt) {
        this.sentAt = sentAt;
        return this;
    }
}
