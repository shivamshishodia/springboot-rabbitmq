package com.shishodia.rabbitmq.producer.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shishodia.rabbitmq.producer.dto.Deals;

@CrossOrigin
@RestController
@RequestMapping(value = "api/v1")
public interface ProducerController {

    @PostMapping(value = "/deals", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deals(@RequestBody Deals deals);

    @PostMapping(value = "/deals/confirms", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> dealsPublishConfirm(@RequestBody Deals deals);
    
}
