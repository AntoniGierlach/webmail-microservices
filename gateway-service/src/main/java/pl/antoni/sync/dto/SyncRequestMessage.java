package pl.antoni.sync.dto;

import java.io.Serializable;

public class SyncRequestMessage implements Serializable {
    private Long jobId;

    public Long getJobId() {
        return jobId;
    }

    public SyncRequestMessage setJobId(Long jobId) {
        this.jobId = jobId;
        return this;
    }
}
