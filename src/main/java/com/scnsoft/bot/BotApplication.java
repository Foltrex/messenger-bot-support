package com.scnsoft.bot;

import javax.xml.transform.Source;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

// @EnableBinding(Sink.class)
@SpringBootApplication
public class BotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BotApplication.class, args);
	}

	// @StreamListener(target = Sink.INPUT)
	// public void logfast(String message) {
	// 	System.out.println(message);
	// }
}
