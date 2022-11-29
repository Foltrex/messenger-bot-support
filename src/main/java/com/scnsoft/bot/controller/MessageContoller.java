package com.scnsoft.bot.controller;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.exception.MessageDecrypterException;
import com.scnsoft.bot.exception.ServiceException;
import com.scnsoft.bot.repository.MessageRepository;
import com.scnsoft.bot.service.MessageCryptoService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/bot/messages")
@Log4j2
public record MessageContoller(
    MessageCryptoService messageCryptoService, 
    MessageRepository messageRepository
) {

    @PostMapping
    public Message receive(@RequestBody Message message) throws ServiceException, MessageDecrypterException {
        String decryptedMessage = messageCryptoService.decrypt(message);
        log.info(decryptedMessage);
        // Message decryptedHelloMessage = messageCryptoService.decryptRSA(message);
        // log.info("Decrypted hello message: " + decryptedHelloMessage);
        return null;
    }

    // @GetMapping
    // public Message receive() throws ServiceException, MessageDecrypterException {
    //     UUID id = UUID.fromString("dd251afa-a42c-4ab9-bcb4-f19181a58248");
    //     Message message = messageRepository.findById(id).get();
    //     log.info(messageCryptoService.decrypt(message));
    //     // Message decryptedHelloMessage = messageCryptoService.decryptRSA(message);
    //     // log.info("Decrypted hello message: " + decryptedHelloMessage);
    //     return null;
    // }
}
