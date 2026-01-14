package pl.antoni.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<ApiError.FieldErrorItem> items = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapFieldError)
                .collect(Collectors.toList());

        ApiError body = base(HttpStatus.BAD_REQUEST, "Validation failed", req.getRequestURI())
                .setFieldErrors(items);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<ApiError> handleMail(MailException ex, HttpServletRequest req) {
        String msg = "Mail send failed";
        ApiError body = base(HttpStatus.BAD_GATEWAY, msg, req.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleOther(Exception ex, HttpServletRequest req) {
        String msg = ex.getClass().getSimpleName();
        if (ex.getMessage() != null && !ex.getMessage().isBlank()) {
            msg = msg + ": " + ex.getMessage();
        }
        ApiError body = base(HttpStatus.INTERNAL_SERVER_ERROR, msg, req.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
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
