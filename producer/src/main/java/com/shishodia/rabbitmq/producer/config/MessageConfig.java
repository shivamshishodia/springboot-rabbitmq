package com.shishodia.rabbitmq.producer.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    public static final String EXCHANGE = "ex.deals";

    public static final String QUEUE_ACTIVE = "q.active";
    public static final String ROUTING_KEY_ACTIVE = "active";
    
    public static final String QUEUE_CLOSED = "q.closed";
    public static final String ROUTING_KEY_CLOSED = "closed";

    // Dead letter configs.

    public static final String EXCHANGE_DEAD = "ex.dead";
    public static final String QUEUE_DEAD = "q.dead";
    public static final String ROUTING_KEY_DEAD = "dead";

    // Exchanges.

	@Bean
	public DirectExchange deadLetterExchange() {
		return new DirectExchange(EXCHANGE_DEAD);
	}

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    // Queues.

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(QUEUE_DEAD).build();
    }

    @Bean
    public Queue queueActive() {
        return QueueBuilder.durable(QUEUE_ACTIVE)
                .withArgument("x-dead-letter-exchange", EXCHANGE_DEAD)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY_DEAD).build();
    }

    @Bean
    public Queue queueClosed() {
        return QueueBuilder.durable(QUEUE_CLOSED)
                .withArgument("x-dead-letter-exchange", EXCHANGE_DEAD)
                .withArgument("x-dead-letter-routing-key", ROUTING_KEY_DEAD).build();
    }

    // Bindings.

    @Bean
	Binding deadLetterQueueBinding() {
		return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(ROUTING_KEY_DEAD);
	}

    @Bean
    public Binding bindingActive() {
        return BindingBuilder.bind(queueActive()).to(exchange()).with(ROUTING_KEY_ACTIVE);
    }

    @Bean
    public Binding bindingClosed() {
        return BindingBuilder.bind(queueClosed()).to(exchange()).with(ROUTING_KEY_CLOSED);
    }

    // Message converter.

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    // Rabbit MQ config.

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
    
}
