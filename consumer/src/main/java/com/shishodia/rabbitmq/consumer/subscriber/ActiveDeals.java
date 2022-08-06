package com.shishodia.rabbitmq.consumer.subscriber;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.shishodia.rabbitmq.consumer.config.MessageConfig;
import com.shishodia.rabbitmq.consumer.dto.Deals;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ActiveDeals {

    @RabbitListener(queues = MessageConfig.QUEUE_ACTIVE)
    public void consumeFeed(Deals product) {
        try {
            log.info("Product recieved by Inventory: " + product.toString());
        } catch (Exception e) {
            log.warn("An exception occurred: " + e);
        }
    }
    
}
