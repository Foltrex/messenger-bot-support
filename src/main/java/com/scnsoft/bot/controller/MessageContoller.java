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

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/bot/messages")
public record MessageContoller(
    AesAlgorithm aesAlgorithm,
    ChatService chatService,
    MessageRepository messageRepository
) {

    private static final int KEY_SIZE = 16;

    @PostMapping
    public List<Message> respondOnBotMessage(@RequestBody Message message) {
        String decryptedMessageData = new String(aesAlgorithm.decrypt(message), StandardCharsets.UTF_8);

        log.info(decryptedMessageData);

        UUID botId = message.getReceiver();
        UUID chatId = message.getChat();
        Chat chat = chatService.findById(chatId);
        List<Message> messages = chat.getMembers()
            .stream()
            .filter(member -> !Objects.equals(member.getId(), botId))
            .map(member -> {
                String generatedNonce = aesAlgorithm.generateNonce(KEY_SIZE);
                
                return Message.builder()
                    .sender(botId)
                    .receiver(member.getId())
                    .chat(chatId)
                    .type(MessageType.whisper)
                    .data(decryptedMessageData)
                    .build();
            })
            .peek(decryptedMessage -> {
                String encryptedMessageData = new String(aesAlgorithm.encrypt(message), StandardCharsets.UTF_8);
                decryptedMessage.setData(encryptedMessageData);
            })
            .toList();

        return messages;
    }
}
