package pl.antoni.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    public static final String EXCHANGE = "mailpilot.exchange";
    public static final String SYNC_REQUEST_QUEUE = "sync.request.queue";
    public static final String SYNC_RESULT_QUEUE = "sync.result.queue";
    public static final String SYNC_REQUEST_KEY = "sync.request";
    public static final String SYNC_RESULT_KEY = "sync.result";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue syncRequestQueue() {
        return QueueBuilder.durable(SYNC_REQUEST_QUEUE).build();
    }

    @Bean
    public Queue syncResultQueue() {
        return QueueBuilder.durable(SYNC_RESULT_QUEUE).build();
    }

    @Bean
    public Binding bindSyncRequest(
            DirectExchange exchange,
            @Qualifier("syncRequestQueue") Queue syncRequestQueue
    ) {
        return BindingBuilder.bind(syncRequestQueue).to(exchange).with(SYNC_REQUEST_KEY);
    }

    @Bean
    public Binding bindSyncResult(
            DirectExchange exchange,
            @Qualifier("syncResultQueue") Queue syncResultQueue
    ) {
        return BindingBuilder.bind(syncResultQueue).to(exchange).with(SYNC_RESULT_KEY);
    }
}
