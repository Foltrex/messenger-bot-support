package com.scnsoft.bot.service;


import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

import com.scnsoft.bot.data.SecretsFileReader;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.exception.MessageDecrypterException;
import com.scnsoft.bot.logic.decrypter.MessageDecrypter;
import com.scnsoft.bot.logic.decrypter.impl.AesMessageDecrypter;
import com.scnsoft.bot.logic.decrypter.impl.RsaMessageDecrypter;
import com.scnsoft.bot.logic.encrtyper.MessageEncrypter;
import com.scnsoft.bot.logic.encrtyper.impl.AesMessageEncrypter;
import com.scnsoft.bot.logic.encrtyper.impl.RsaMessageEncrypter;
import com.scnsoft.bot.repository.ChatRepository;
import com.scnsoft.bot.repository.CustomerRepository;
import com.scnsoft.bot.repository.MessageRepository;
import com.scnsoft.bot.repository.UtilRepository;

@Service
public record MessageCryptoService(
    ChatRepository chatRepository,
    MessageRepository messageRepository
) {

    public String decrypt(Message message) throws MessageDecrypterException {
        MessageDecrypter decrypter = new AesMessageDecrypter(
            messageRepository,
            chatRepository,
            new RsaMessageDecrypter(new SecretsFileReader())
        );

        return new String(decrypter.decrypt(message), StandardCharsets.UTF_8);
    }

    public Message encrypt(Message message) {
        MessageEncrypter encrypter = switch (message.getType()) {
            case hello -> new RsaMessageEncrypter();
            case whisper -> new AesMessageEncrypter();
            default -> throw new IllegalArgumentException("Invalid message type for bot");
        };

        return encrypter.encrypt(message);
    }
}
