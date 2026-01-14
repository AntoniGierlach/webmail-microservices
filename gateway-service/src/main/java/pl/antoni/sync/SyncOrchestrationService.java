package pl.antoni.sync;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.antoni.amqp.AmqpConfig;
import pl.antoni.sync.dto.SyncRequestMessage;

import java.util.List;

@Service
public class SyncOrchestrationService {

    private final SyncJobRepository repo;
    private final RabbitTemplate rabbit;

    public SyncOrchestrationService(SyncJobRepository repo, RabbitTemplate rabbit) {
        this.repo = repo;
        this.rabbit = rabbit;
    }

    @Transactional
    public SyncJob requestSync() {
        SyncJob job = new SyncJob().setStatus(SyncJobStatus.PENDING);
        SyncJob saved = repo.save(job);

        SyncRequestMessage msg = new SyncRequestMessage().setJobId(saved.getId());
        rabbit.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.SYNC_REQUEST_KEY, msg);

        return saved;
    }

    @Transactional(readOnly = true)
    public List<SyncJob> listJobs() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public SyncJob getJob(Long id) {
        return repo.findById(id).orElseThrow(() -> new SyncJobNotFoundException(id));
    }
}
