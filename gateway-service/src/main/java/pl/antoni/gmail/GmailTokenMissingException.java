package pl.antoni.gmail;

public class GmailTokenMissingException extends RuntimeException {
    public GmailTokenMissingException(String email) {
        super("Missing Gmail token for: " + email);
    }
}
