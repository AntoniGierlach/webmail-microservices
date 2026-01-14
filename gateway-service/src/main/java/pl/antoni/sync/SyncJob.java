package pl.antoni.sync;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "sync_jobs")
public class SyncJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private SyncJobStatus status;

    @Column(nullable = false)
    private Instant requestedAt;

    private Instant finishedAt;

    @Column(length = 500)
    private String message;

    @Column(nullable = false)
    private int inboxCount;

    @PrePersist
    void onCreate() {
        this.requestedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public SyncJobStatus getStatus() {
        return status;
    }

    public SyncJob setStatus(SyncJobStatus status) {
        this.status = status;
        return this;
    }

    public Instant getRequestedAt() {
        return requestedAt;
    }

    public Instant getFinishedAt() {
        return finishedAt;
    }

    public SyncJob setFinishedAt(Instant finishedAt) {
        this.finishedAt = finishedAt;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public SyncJob setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getInboxCount() {
        return inboxCount;
    }

    public SyncJob setInboxCount(int inboxCount) {
        this.inboxCount = inboxCount;
        return this;
    }
}
