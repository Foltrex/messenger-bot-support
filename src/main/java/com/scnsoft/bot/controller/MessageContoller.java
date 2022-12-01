package com.scnsoft.bot.controller;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scnsoft.bot.entity.Chat;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.entity.Message.MessageType;
import com.scnsoft.bot.exception.MessageDecrypterException;
import com.scnsoft.bot.exception.ServiceException;
import com.scnsoft.bot.logic.crypt.AesAlgorithm;
import com.scnsoft.bot.repository.MessageRepository;
import com.scnsoft.bot.service.ChatService;
import com.scnsoft.bot.service.MessageService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/bot/messages")
public record MessageContoller(
    AesAlgorithm aesAlgorithm,
    ChatService chatService,
    MessageService messageService
) {

    @PostMapping
    public List<Message> respondOnBotMessage(@RequestBody Message message) {
        return messageService.respondOnBotMessage(message);
    }
}
