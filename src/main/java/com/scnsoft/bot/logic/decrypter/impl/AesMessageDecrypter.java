package com.scnsoft.bot.logic.decrypter.impl;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

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
    
    private static final byte UNDERSCORE_UTF16_BYTE_NUMBER = 95;

    @Override
    public byte[] decrypt(Message message) throws MessageDecrypterException {
        try {
            String nonce = message.getNonce();
            SecretKey aesKey = getAesSecretKey(message);
            IvParameterSpec ivParameterSpec = getInitializationVector(nonce);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

            byte[] decodedMessageData = Base64.decodeBase64(message.getData());
            return cipher.doFinal(decodedMessageData);
            
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

        // Message decryptedHelloMessage = rsaMessageDecrypter.decrypt(helloMessage);
        // String helloMessageData = decryptedHelloMessage.getData();
        // String helloMessageData = new String(decryptedHelloMessage.getData());
        // byte[] decryptedRsaBytes = helloMessageData.getBytes(StandardCharsets.UTF_16);
        byte[] decryptedRsaBytes = rsaMessageDecrypter.decrypt(helloMessage);

        int underscoreIndex = getDoubleUnderscoreIndex(decryptedRsaBytes);
        byte[] key = IntStream
            .range(underscoreIndex + 1, decryptedRsaBytes.length)
            .map(i -> decryptedRsaBytes[i])
            .filter(el -> el != 0)
            .collect(ByteArrayOutputStream::new, (baos, i) -> baos.write((byte) i),
                (baos1, baos2) -> baos1.write(baos2.toByteArray(), 0, baos2.size()))
            .toByteArray();

        return new SecretKeySpec(key, "AES");
    }

    private int getDoubleUnderscoreIndex(byte[] decryptedRsaBytesArray) {
        boolean isPreviousSymbolUnderscore = false;
        for (int i = 0; i < decryptedRsaBytesArray.length; i++) {
            if (decryptedRsaBytesArray[i] == UNDERSCORE_UTF16_BYTE_NUMBER) {
                if (isPreviousSymbolUnderscore) {
                    return i;
                } else {
                    isPreviousSymbolUnderscore = true;
                }
            }
        }

        return -1;
    }
}
