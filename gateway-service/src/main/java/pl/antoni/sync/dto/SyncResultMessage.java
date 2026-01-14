package pl.antoni.sync.dto;

import java.io.Serializable;

public class SyncResultMessage implements Serializable {
    private Long jobId;
    private String status;
    private String message;
    private int inboxCount;

    public Long getJobId() {
        return jobId;
    }

    public SyncResultMessage setJobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SyncResultMessage setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SyncResultMessage setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getInboxCount() {
        return inboxCount;
    }

    public SyncResultMessage setInboxCount(int inboxCount) {
        this.inboxCount = inboxCount;
        return this;
    }
}
