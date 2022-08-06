package com.shishodia.rabbitmq.producer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.shishodia.rabbitmq.producer.dto.Deals;
import com.shishodia.rabbitmq.producer.dto.Response;
import com.shishodia.rabbitmq.producer.service.ProducerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ProducerControllerImpl implements ProducerController {

    @Autowired
    private ProducerService producerService;

    @Override
    public ResponseEntity<Object> deals(Deals deals) {
        Response apiResponse = new Response();

        if (deals.getProducts().isEmpty()) {
            apiResponse = new Response(HttpStatus.BAD_REQUEST, "No product(s) present in your deal.");
            return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        try {
            producerService.deals(deals);
            apiResponse = new Response(HttpStatus.OK, "Your deal has been processed.");
            return new ResponseEntity<Object>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            log.warn("An exception occurred: " + e);
            apiResponse = new Response(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while handling your deal.");
            return new ResponseEntity<Object>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
