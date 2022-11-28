package com.scnsoft.bot.service;


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
    UtilRepository utilRepository,
    ChatRepository chatRepository,
    CustomerRepository customerRepository,
    MessageRepository messageRepository
) {

    public Message decrypt(Message message) throws MessageDecrypterException {
        MessageDecrypter decrypter = switch (message.getType()) {
            case hello -> new RsaMessageDecrypter(new SecretsFileReader());
            case whisper -> new AesMessageDecrypter(
                    messageRepository,
                    chatRepository,
                    new RsaMessageDecrypter(new SecretsFileReader())
                );
            default -> throw new IllegalArgumentException("Invalid message type for bot");
        };

        return decrypter.decrypt(message);
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
