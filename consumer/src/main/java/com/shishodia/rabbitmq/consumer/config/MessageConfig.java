package com.shishodia.rabbitmq.consumer.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue queueActive() {
        return new Queue(QUEUE_ACTIVE);
    }

    @Bean
    public Queue queueClosed() {
        return new Queue(QUEUE_CLOSED);
    }


    @Bean
    public Binding bindingActive() {
        return BindingBuilder.bind(queueActive()).to(exchange()).with(ROUTING_KEY_ACTIVE);
    }

    @Bean
    public Binding bindingClosed() {
        return BindingBuilder.bind(queueClosed()).to(exchange()).with(ROUTING_KEY_CLOSED);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }
    
}
