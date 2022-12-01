package com.scnsoft.bot.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

import com.scnsoft.bot.entity.Chat;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.entity.Message.MessageType;
import com.scnsoft.bot.logic.crypt.AesAlgorithm;
import com.scnsoft.bot.repository.ChatRepository;
import com.scnsoft.bot.service.MessageService;

public record MessageServiceImpl(
    AesAlgorithm aesAlgorithm,
    ChatRepository chatRepository
) implements MessageService {

    private static final int KEY_SIZE = 16;

    @Override
    public List<Message> respondOnBotMessage(Message message) {
        String decryptedMessageData = new String(aesAlgorithm.decrypt(message), StandardCharsets.UTF_8);

        UUID botId = message.getReceiver();
        UUID chatId = message.getChat();
        Chat chat = chatRepository
            .findById(chatId)
            .orElseThrow(NoSuchElementException::new);
        
        List<Message> messages = createEcryptedMessageForEachChatMemberFromBot(decryptedMessageData, chat, botId);
        return messages;
    }
    
    private List<Message> createEcryptedMessageForEachChatMemberFromBot(String decryptedMessageData, Chat chat, UUID botId) {
        List<Message> messages = chat.getMembers()
            .stream()
            .filter(member -> !Objects.equals(member.getId(), botId))
            .map(member -> {
                String generatedNonce = aesAlgorithm.generateNonce(KEY_SIZE);
                
                return Message.builder()
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

        return messages;
    }
}
