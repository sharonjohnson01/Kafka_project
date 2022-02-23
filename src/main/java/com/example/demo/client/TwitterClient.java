package com.example.demo.client;


import java.util.List;
import java.util.concurrent.BlockingQueue;


import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;


@Component
public class TwitterClient {
	
	
	public Client createTwitterClient( BlockingQueue<String> msgQueue){
	
		String consumerKey="mBaQKU2LBkd2QGhGTem7h773X";
		String consumerSecret="1UkvhNySw1SuVUffDPngY79k1ahcPvRvfXwzYiJTT9XLIedC0h";
		String token="47911651-5esOzVpEIddNWVNkOSxq4wx3emp4ab1SSRE8lCYH9";
		String secret="w74bqQ2IgyB05XNCT2LRLUeeS52ZG6FhNuBFC7K7P3U0n";
	

	/** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
	Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
	StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
	
	// Optional: set up some followings and track terms

	List<String> terms = Lists.newArrayList("kafka");
	hosebirdEndpoint.trackTerms(terms);


	// These secrets should be read from a config file
	Authentication hosebirdAuth = new OAuth1(consumerKey, consumerSecret, token, secret);
	
	ClientBuilder builder = new ClientBuilder()
			  .name("Hosebird-Client-01")                              // optional: mainly for the logs
			  .hosts(hosebirdHosts)
			  .authentication(hosebirdAuth)
			  .endpoint(hosebirdEndpoint)
			  .processor(new StringDelimitedProcessor(msgQueue));                  // optional: use this if you want to process client events

			Client hosebirdClient = builder.build();
			// Attempts to establish a connection.
			return hosebirdClient;

}
}
