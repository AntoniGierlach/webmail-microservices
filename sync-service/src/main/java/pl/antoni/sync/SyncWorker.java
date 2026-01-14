package pl.antoni.sync;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import pl.antoni.amqp.AmqpConfig;
import pl.antoni.sync.dto.SyncRequestMessage;
import pl.antoni.sync.dto.SyncResultMessage;

import java.util.concurrent.ThreadLocalRandom;

@Component
public class SyncWorker {

    private final RabbitTemplate rabbit;

    public SyncWorker(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @RabbitListener(queues = AmqpConfig.SYNC_REQUEST_QUEUE)
    public void onRequest(SyncRequestMessage msg) {
        if (msg.getJobId() == null) {
            return;
        }

        int inboxCount = ThreadLocalRandom.current().nextInt(1, 200);

        SyncResultMessage result = new SyncResultMessage()
                .setJobId(msg.getJobId())
                .setStatus("SUCCESS")
                .setMessage("Sync completed")
                .setInboxCount(inboxCount);

        rabbit.convertAndSend(AmqpConfig.EXCHANGE, AmqpConfig.SYNC_RESULT_KEY, result);
    }
}
