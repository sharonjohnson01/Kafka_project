package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    private static final String TOPIC = "twitter_tweets";

    public void sendStringMessage(String name) {
    	System.out.println("Tweeting each time");
        kafkaTemplate.send(TOPIC, name);
    }
}
