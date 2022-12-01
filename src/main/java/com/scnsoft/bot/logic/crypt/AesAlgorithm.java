package com.scnsoft.bot.logic.crypt;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.scnsoft.bot.entity.Chat;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.entity.Message.MessageType;
import com.scnsoft.bot.exception.MessageDecrypterException;
import com.scnsoft.bot.exception.MessageEncrypterException;
import com.scnsoft.bot.repository.ChatRepository;
import com.scnsoft.bot.repository.MessageRepository;

@Component
public record AesAlgorithm(
    MessageRepository messageRepository,
    ChatRepository chatRepository,
    RsaAlgorithm rsaAlgorithm
) {
        
    private static final byte UNDERSCORE_UTF16_BYTE_NUMBER = 95;

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";


    
    public byte[] encrypt(Message message) throws MessageEncrypterException {
        try {
            String nonce = message.getNonce();
            SecretKey aesKey = getAesSecretKey(message);
            IvParameterSpec ivParameterSpec = getInitializationVector(nonce);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
            String messageData = message.getData();

            byte[] cipherText = cipher.doFinal(messageData.getBytes(StandardCharsets.UTF_8));
            return Base64.encodeBase64(cipherText);
        } catch (Exception e) {
            throw new MessageEncrypterException(e);
        }
    }

    
    public byte[] decrypt(Message message) throws MessageDecrypterException {
        try {
            String nonce = message.getNonce();
            SecretKey aesKey = getAesSecretKey(message);
            IvParameterSpec ivParameterSpec = getInitializationVector(nonce);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, aesKey, ivParameterSpec);

            byte[] decodedMessageData = Base64.decodeBase64(message.getData());
            return cipher.doFinal(decodedMessageData);
            
        } catch (Exception e) {
            throw new MessageDecrypterException(e);
        }
    }

    
    public String generateNonce(int size) {
        byte[] nonce = new byte[size];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(nonce);
        return Base64.encodeBase64String(nonce);
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
            
        byte[] decryptedRsaBytes = rsaAlgorithm.decrypt(helloMessage);
        int underscoreIndex = getDoubleUnderscoreIndex(decryptedRsaBytes);
        byte[] key = Arrays.copyOfRange(decryptedRsaBytes, underscoreIndex + 1, decryptedRsaBytes.length);
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
