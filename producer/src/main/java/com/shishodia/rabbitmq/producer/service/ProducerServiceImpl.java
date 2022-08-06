package com.shishodia.rabbitmq.producer.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

import com.shishodia.rabbitmq.producer.config.MessageConfig;
import com.shishodia.rabbitmq.producer.dto.Deals;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    private RabbitTemplate rmqTemplate;

    // This can also be provided under `MessageConfig.template()`.
    @PostConstruct
    private void configureCallbacks() {
        rmqTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("Publish confirms received: (correlationData=" + correlationData.getId() + ", ack=" + ack + ", cause=" + cause + ")");
            }
        });
        rmqTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage arg0) {
                log.info("ack=" + arg0);
            }
        });
    }

    @Override
    public void deals(Deals deals) throws Exception {
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
            throw new Exception("Unable to enqueue deals.");
        }
    }

    @Override
    public CorrelationData dealsPublishConfirm(Deals deals) throws Exception {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        try {
            if (!deals.getIsClosed()) {
                rmqTemplate.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY_ACTIVE, deals, correlationData);
                log.info("Deal routed to active queue: " + deals.toString());
            } else {
                rmqTemplate.convertAndSend(MessageConfig.EXCHANGE, MessageConfig.ROUTING_KEY_CLOSED, deals, correlationData);
                log.info("Deal routed to closed queue: " + deals.toString());
            }
        } catch (Exception e) {
            log.warn("ProducerServiceImpl (deals) > An exception occurred: " + e);
            throw new Exception("Unable to enqueue deals.");
        }
        return correlationData;
    }

}
