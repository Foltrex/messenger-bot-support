package com.scnsoft.bot.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scnsoft.bot.dto.MessageDto;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.logic.MessengerBot;
import com.scnsoft.bot.logic.crypt.AesAlgorithm;
import com.scnsoft.bot.service.MessageService;

@RestController
@RequestMapping("/bot/messages")
public record MessageContoller(
    MessageService messageService,
    MessengerBot messengerBot
) {

    @PostMapping
    public List<MessageDto> respondOnBotMessage(@RequestBody MessageDto message) {
        
        return messageService.respondOnBotMessage(message);
    }
}
