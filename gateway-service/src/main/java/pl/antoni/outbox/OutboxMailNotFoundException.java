package pl.antoni.outbox;

public class OutboxMailNotFoundException extends RuntimeException {
    public OutboxMailNotFoundException(Long id) {
        super("Outbox mail not found: id=" + id);
    }
}
