package pl.antoni.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.antoni.contacts.ContactNotFoundException;
import pl.antoni.contacts.DuplicateEmailException;
import pl.antoni.sync.SyncJobNotFoundException;
import pl.antoni.outbox.MailServiceUnavailableException;
import pl.antoni.outbox.OutboxMailNotFoundException;



import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ContactNotFoundException.class)
    public ResponseEntity<ApiError> handleContactNotFound(ContactNotFoundException ex, HttpServletRequest req) {
        ApiError body = base(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ApiError> handleDuplicateEmail(DuplicateEmailException ex, HttpServletRequest req) {
        ApiError body = base(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(SyncJobNotFoundException.class)
    public ResponseEntity<ApiError> handleSyncJobNotFound(SyncJobNotFoundException ex, HttpServletRequest req) {
        ApiError body = base(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ApiError.FieldErrorItem> items = ex.getBindingResult().getFieldErrors().stream()
                .map(this::mapFieldError)
                .collect(java.util.stream.Collectors.toList());

        ApiError body = base(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI())
                .setFieldErrors(items);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex, HttpServletRequest req) {
        ApiError body = base(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    @ExceptionHandler(MailServiceUnavailableException.class)
    public ResponseEntity<ApiError> handleMailService(MailServiceUnavailableException ex, HttpServletRequest req) {
        ApiError body = base(HttpStatus.BAD_GATEWAY, ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }

    @ExceptionHandler(OutboxMailNotFoundException.class)
    public ResponseEntity<ApiError> handleOutboxNotFound(OutboxMailNotFoundException ex, HttpServletRequest req) {
        ApiError body = base(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }


    private ApiError.FieldErrorItem mapFieldError(FieldError fe) {
        return new ApiError.FieldErrorItem()
                .setField(fe.getField())
                .setMessage(fe.getDefaultMessage());
    }

    private ApiError base(HttpStatus status, String message, String path) {
        return new ApiError()
                .setTimestamp(Instant.now())
                .setStatus(status.value())
                .setError(status.getReasonPhrase())
                .setMessage(message)
                .setPath(path);
    }
}
