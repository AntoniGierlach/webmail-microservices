package pl.antoni.sync;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.antoni.amqp.AmqpConfig;
import pl.antoni.sync.dto.SyncResultMessage;

import java.time.Instant;

@Component
public class SyncResultListener {

    private final SyncJobRepository repo;

    public SyncResultListener(SyncJobRepository repo) {
        this.repo = repo;
    }

    @RabbitListener(queues = AmqpConfig.SYNC_RESULT_QUEUE)
    @Transactional
    public void onResult(SyncResultMessage msg) {
        if (msg.getJobId() == null) {
            return;
        }

        SyncJob job = repo.findById(msg.getJobId()).orElse(null);
        if (job == null) {
            return;
        }

        SyncJobStatus status = parseStatus(msg.getStatus());
        job.setStatus(status)
                .setFinishedAt(Instant.now())
                .setMessage(msg.getMessage())
                .setInboxCount(msg.getInboxCount());

        repo.save(job);
    }

    private SyncJobStatus parseStatus(String s) {
        if (s == null) return SyncJobStatus.FAILED;
        try {
            return SyncJobStatus.valueOf(s);
        } catch (Exception e) {
            return SyncJobStatus.FAILED;
        }
    }
}
