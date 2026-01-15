package pl.antoni.outbox;

public class MailServiceUnavailableException extends RuntimeException {
    public MailServiceUnavailableException(String message) {
        super(message);
    }
}
