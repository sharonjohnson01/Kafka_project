package com.example.demo;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.client.TwitterClient;
import com.example.demo.service.KafkaProducerService;
import com.twitter.hbc.core.Client;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	
	@Autowired
	TwitterClient twitterClient;
	
	@Autowired
	KafkaProducerService kafkaProducerService;

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setBannerMode(org.springframework.boot.Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Entered into the Run Method");
		BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
		Client client = twitterClient.createTwitterClient(msgQueue);
		client.connect();
		
		while (!client.isDone()) {
			String msg =null;
			try {
			   msg = msgQueue.poll(5, TimeUnit.SECONDS);
			}catch (InterruptedException e) {
				e.printStackTrace();
				client.stop();
			}
			
			if(msg != null) {
				System.out.println("The Message is "+msg);
				kafkaProducerService.sendStringMessage(msg);
			}
			 
		}
	}
	
	

}
