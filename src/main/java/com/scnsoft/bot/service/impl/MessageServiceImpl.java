package com.scnsoft.bot.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import javax.websocket.server.ServerEndpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.scnsoft.bot.dto.MessageDto;
import com.scnsoft.bot.dto.MessageDto.MessageType;
import com.scnsoft.bot.entity.Chat;
import com.scnsoft.bot.model.Message;
import com.scnsoft.bot.logic.MessengerBot;
import com.scnsoft.bot.logic.crypt.AesAlgorithm;
import com.scnsoft.bot.service.MessageService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public record MessageServiceImpl(
    AesAlgorithm aesAlgorithm,
    RestTemplate restTemplate,
    MessengerBot messengerBot
) implements MessageService {

    private static final int KEY_SIZE = 16;

    // TODO: move this url to application property as source application url
    private static final String MESSENGER_URL = "http://localhost:8080";

    @Override
    public List<MessageDto> respondOnBotMessage(MessageDto messageDto) {
        String decryptedMessageData = new String(aesAlgorithm.decrypt(messageDto), StandardCharsets.UTF_8);
        log.info("decrypted message: {}", decryptedMessageData);

        MessageDto decryptedMessage = new MessageDto(messageDto);
        decryptedMessage.setData(decryptedMessageData);

        Message botAnswer = messengerBot.handleIncommingMessage(messageDto.toMessage());

        UUID botId = messageDto.getReceiver();
        UUID chatId = messageDto.getChat();
        String chatUrl = MESSENGER_URL + "/chat/" + chatId;
        ResponseEntity<Chat> chatResponse = restTemplate.getForEntity(chatUrl, Chat.class);
        Chat chat = chatResponse.getBody();
        
        return createEcryptedMessageForEachChatMemberFromBot(botAnswer.data(), chat, botId);
    }
    
    private List<MessageDto> createEcryptedMessageForEachChatMemberFromBot(String decryptedMessageData, Chat chat, UUID botId) {
        List<MessageDto> messageDtos = chat.getMembers()
            .stream()
            .filter(member -> !Objects.equals(member.getId(), botId))
            .map(member -> {
                String generatedNonce = aesAlgorithm.generateNonce(KEY_SIZE);
                
                return MessageDto.builder()
                    .sender(botId)
                    .receiver(member.getId())
                    .chat(chat.getId())
                    .type(MessageType.whisper)
                    .data(decryptedMessageData)
                    .nonce(generatedNonce)
                    .build();
            })
            .peek(decryptedMessage -> {
                String encryptedMessageData = new String(aesAlgorithm.encrypt(decryptedMessage), StandardCharsets.UTF_8);
                decryptedMessage.setData(encryptedMessageData);
            })
            .toList();

        return messageDtos;
    }
}
