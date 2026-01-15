package pl.antoni.outbox.dto;

public class MailResultMessage {
    private Long outboxId;
    private String status;
    private String error;

    public Long getOutboxId() {
        return outboxId;
    }

    public MailResultMessage setOutboxId(Long outboxId) {
        this.outboxId = outboxId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public MailResultMessage setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getError() {
        return error;
    }

    public MailResultMessage setError(String error) {
        this.error = error;
        return this;
    }
}
