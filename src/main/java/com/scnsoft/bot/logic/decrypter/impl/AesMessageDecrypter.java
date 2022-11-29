package com.scnsoft.bot.logic.decrypter.impl;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;

import com.fasterxml.jackson.databind.JsonSerializable.Base;
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
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            // Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);


            byte[] decodedMessageData = Base64.decodeBase64(message.getData());
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
        byte[] initializationVector = Base64.decodeBase64(nonce);
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
        // String helloMessageData = decryptedHelloMessage.getData();
        String helloMessageData = new String(decryptedHelloMessage.getData());
        String[] helloMessageDataParts = helloMessageData.split("__");
        String aesKey = helloMessageDataParts[1];
        // log.info(aesKey);
        
        // byte[] key = aesKey.getBytes(StandardCharsets.UTF_8);
        byte[] key = aesKey.getBytes(StandardCharsets.UTF_8);

        SecretKey secretKey = new SecretKeySpec(key, "AES");
        // SecretKeySpec secretKey = null;
        // try {
        //     SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        //     KeySpec keySpec = new PBEKeySpec(aesKey.toCharArray());
        //     SecretKey tmp = factory.generateSecret(keySpec);
        //     secretKey = new SecretKeySpec(tmp.getEncoded(), "AES");
        // } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
        return secretKey;
    }
}
