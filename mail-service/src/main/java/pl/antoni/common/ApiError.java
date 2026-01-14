package pl.antoni.common;

import java.time.Instant;
import java.util.List;

public class ApiError {

    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldErrorItem> fieldErrors;

    public Instant getTimestamp() {
        return timestamp;
    }

    public ApiError setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ApiError setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getError() {
        return error;
    }

    public ApiError setError(String error) {
        this.error = error;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiError setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getPath() {
        return path;
    }

    public ApiError setPath(String path) {
        this.path = path;
        return this;
    }

    public List<FieldErrorItem> getFieldErrors() {
        return fieldErrors;
    }

    public ApiError setFieldErrors(List<FieldErrorItem> fieldErrors) {
        this.fieldErrors = fieldErrors;
        return this;
    }

    public static class FieldErrorItem {

        private String field;
        private String message;

        public String getField() {
            return field;
        }

        public FieldErrorItem setField(String field) {
            this.field = field;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public FieldErrorItem setMessage(String message) {
            this.message = message;
            return this;
        }
    }
}
