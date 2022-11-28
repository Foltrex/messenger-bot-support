package com.scnsoft.bot.logic.decrypter.impl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.scnsoft.bot.entity.Chat;
import com.scnsoft.bot.entity.Customer;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.entity.Message.MessageType;
import com.scnsoft.bot.exception.MessageDecrypterException;
import com.scnsoft.bot.logic.decrypter.MessageDecrypter;
import com.scnsoft.bot.repository.ChatRepository;
import com.scnsoft.bot.repository.MessageRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public record AesMessageDecrypter(
    MessageRepository messageRepository,
    ChatRepository chatRepository,
    RsaMessageDecrypter rsaMessageDecrypter
) implements MessageDecrypter {

    @Override
    public Message decrypt(Message message) throws MessageDecrypterException {
        try {
            String nonce = message.getNonce();
            SecretKey aesKey = getAesSecretKey(message);
            IvParameterSpec ivParameterSpec = getInitializationVector(nonce);
            Cipher cipher = Cipher.getInstance("AES/CBC");
            cipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

            Base64.Decoder decoder = Base64.getDecoder();

            byte[] decodedMessageData = decoder.decode(message.getData());
            byte[] decryptedBytes = cipher.doFinal(decodedMessageData);
            String decryptedMessageData = new String(decryptedBytes, StandardCharsets.UTF_8);
            Message decryptedMessage = new Message(message);
            decryptedMessage.setData(decryptedMessageData);
            return decryptedMessage;
            
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | 
                IllegalBlockSizeException | BadPaddingException | 
                InvalidAlgorithmParameterException e) {
            throw new MessageDecrypterException(e);
        }
    }
    
    private IvParameterSpec getInitializationVector(String nonce) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] initializationVector = decoder.decode(nonce);
        return new IvParameterSpec(initializationVector);
    }

    private SecretKey getAesSecretKey(Message message) throws MessageDecrypterException {
        UUID chatCreatorId = chatRepository
            .findById(message.getChat())
            .map(Chat::getCreatorId)
            .orElseThrow();
            
        Message helloMessage = messageRepository
            .findByTypeAndReceiver(MessageType.hello, chatCreatorId)
            .orElseThrow();

        Message decryptedHelloMessage = rsaMessageDecrypter.decrypt(helloMessage);
        String helloMessageData = decryptedHelloMessage.getData();
        String[] helloMessageDataParts = helloMessageData.split("__");
        String aesKey = helloMessageDataParts[1];
        
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedAesKey = decoder.decode(aesKey);
        SecretKey secretKey = new SecretKeySpec(decodedAesKey, "AES");
        return secretKey;
    }
}
