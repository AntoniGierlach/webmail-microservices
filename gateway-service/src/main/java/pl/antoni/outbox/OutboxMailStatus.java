package pl.antoni.outbox;

public enum OutboxMailStatus {
    PENDING,
    SENDING,
    SENT,
    FAILED
}
