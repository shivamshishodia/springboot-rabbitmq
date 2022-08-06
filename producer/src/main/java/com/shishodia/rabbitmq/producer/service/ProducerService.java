package com.shishodia.rabbitmq.producer.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.stereotype.Service;

import com.shishodia.rabbitmq.producer.dto.Deals;

@Service
public interface ProducerService {

    public void deals(Deals deals) throws Exception;
    public CorrelationData dealsPublishConfirm(Deals deals) throws Exception;

}
