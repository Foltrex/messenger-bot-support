package com.scnsoft.bot.bot.handler;

import java.net.URI;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.scnsoft.bot.bot.entity.Message;


@EnableBinding(Sink.class)
public class BotMessageHandler {
    private static final String SOURCE_APPLICATION_URL = "http://localhost:8080/";
    
	@StreamListener(target = Sink.INPUT)
	public void respond(Message message) {
        Message newMessage = new Message();
        HttpEntity<Message> request = new HttpEntity<>(newMessage);
        RestTemplate restTemplate = new RestTemplate();
        String botUrl = SOURCE_APPLICATION_URL + "bots";
        restTemplate.postForEntity(botUrl, request, Message.class, "");
	}
}
