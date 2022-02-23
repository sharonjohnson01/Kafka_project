package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.KafkaProducerService;

@RestController
@RequestMapping("/kafka")
public class KafkaProducerController {

    @Autowired
     KafkaProducerService kafkaProducerService;

    @PostMapping("publish/{name}")
    public ResponseEntity<String> publishMessage(@PathVariable(name = "name") String name) {
        kafkaProducerService.sendStringMessage(name);
        return new ResponseEntity<String>("Published Successfully", HttpStatus.OK);
    }

}
