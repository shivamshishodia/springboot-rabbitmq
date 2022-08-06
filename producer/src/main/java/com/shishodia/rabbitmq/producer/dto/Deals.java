package com.shishodia.rabbitmq.producer.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Deals {

    private Integer id;
    private Boolean isClosed;
    private List<String> products;
    
}
