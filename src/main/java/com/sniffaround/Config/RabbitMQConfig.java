package com.sniffaround.Config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.RetryInterceptorBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.StatelessRetryOperationsInterceptor;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    private static final String EMAIL_QUEUE = "email.queue";
    private static final String DLQ_EMAIL_KEY = "dlq.email";
    private static final String DLQ_EMAIL_EXCHANGE = "dlq.email.exchange";

    @Bean
    public Queue emailQueue() {
        return QueueBuilder
                .durable(EMAIL_QUEUE)
                .withArgument("x-dead-letter-exchange", DLQ_EMAIL_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", DLQ_EMAIL_KEY)
                .build();
    }

    @Bean
    public Queue dlqEmail() {
        return QueueBuilder
                .durable(DLQ_EMAIL_KEY)
                .build();
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DLQ_EMAIL_EXCHANGE);
    }

    @Bean
    public Binding dlqBinding() {
        return BindingBuilder
                .bind(dlqEmail())
                .to(directExchange())
                .with(DLQ_EMAIL_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConvertor() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConvertor());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter jsonMessageConvertor
    ) {

        SimpleRabbitListenerContainerFactory factory =
                new SimpleRabbitListenerContainerFactory();

        factory.setConnectionFactory(connectionFactory);

        factory.setMessageConverter(jsonMessageConvertor);

        factory.setDefaultRequeueRejected(false);

        StatelessRetryOperationsInterceptor retryOperationsInterceptor = RetryInterceptorBuilder.stateless()
                .maxRetries(3)
                .backOffOptions(2000, 2, 1000)
                .recoverer(new RejectAndDontRequeueRecoverer())
                .build();

        factory.setAdviceChain(retryOperationsInterceptor);

        return factory;
    }
}
