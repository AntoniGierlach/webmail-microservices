package pl.antoni.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {

    public static final String EXCHANGE = "mailpilot.exchange";
    public static final String MAIL_SEND_QUEUE = "mail.send.queue";
    public static final String MAIL_RESULT_QUEUE = "mail.result.queue";
    public static final String MAIL_SEND_KEY = "mail.send";
    public static final String MAIL_RESULT_KEY = "mail.result";

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue mailSendQueue() {
        return QueueBuilder.durable(MAIL_SEND_QUEUE).build();
    }

    @Bean
    public Queue mailResultQueue() {
        return QueueBuilder.durable(MAIL_RESULT_QUEUE).build();
    }

    @Bean
    public Binding bindMailSend(DirectExchange exchange, @Qualifier("mailSendQueue") Queue q) {
        return BindingBuilder.bind(q).to(exchange).with(MAIL_SEND_KEY);
    }

    @Bean
    public Binding bindMailResult(DirectExchange exchange, @Qualifier("mailResultQueue") Queue q) {
        return BindingBuilder.bind(q).to(exchange).with(MAIL_RESULT_KEY);
    }
}
