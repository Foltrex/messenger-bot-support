package com.scnsoft.bot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scnsoft.bot.dto.MessageDto;
import com.scnsoft.bot.logic.MessengerBot;
import com.scnsoft.bot.service.MessageService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/messages")
public record MessageContoller(
    MessageService messageService,
    MessengerBot messengerBot
) {

    @PostMapping
    public List<MessageDto> respondOnBotMessage(@RequestBody MessageDto message) {
        log.info("incoming message in controller: {}", message);
        return messageService.respondOnBotMessage(message);
    }
}
