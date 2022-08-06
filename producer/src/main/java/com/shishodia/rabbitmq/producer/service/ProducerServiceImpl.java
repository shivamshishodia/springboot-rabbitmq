package com.shishodia.rabbitmq.producer.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shishodia.rabbitmq.producer.config.MessageConfig;
import com.shishodia.rabbitmq.producer.dto.Deals;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private RabbitTemplate rmqTemplate;

    @Override
    public void deals(Deals deals) {

        try {
            if (!deals.getIsClosed()) {
                rmqTemplate.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY_ACTIVE, deals);
                log.info("Deal routed to active queue: " + deals.toString());
            } else {
                rmqTemplate.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY_CLOSED, deals);
                log.info("Deal routed to closed queue: " + deals.toString());
            }
        } catch (Exception e) {
            log.warn("ProducerServiceImpl (deals) > An exception occurred: " + e);
        }
    }

}
