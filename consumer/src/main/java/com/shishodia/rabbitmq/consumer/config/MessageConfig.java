package com.shishodia.rabbitmq.consumer.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageConfig {

    public static final String QUEUE_ACTIVE = "q.active";
    public static final String QUEUE_CLOSED = "q.closed";

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

}
