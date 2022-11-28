package com.scnsoft.bot.logic.decrypter.impl;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.entity.Utility;
import com.scnsoft.bot.exception.MessageDecrypterException;
import com.scnsoft.bot.logic.decrypter.MessageDecrypter;
import com.scnsoft.bot.repository.UtilRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public record RsaMessageDecrypter(
    UtilRepository utilRepository
) implements MessageDecrypter {

    private static final String BEGIN_PRIVATE_KEY = "-----BEGIN RSA PRIVATE KEY-----";
    private static final String END_PRIVATE_KEY = "-----END RSA PRIVATE KEY-----";

    @Override
    public Message decrypt(Message message) throws MessageDecrypterException {
        try {
            String privateKeyPem = utilRepository
                .findById(Utility.Key.SERVER_USER_SECRET_KEY.name())
                .map(Utility::getUtilValue)
                .orElseThrow(() -> new IllegalArgumentException("Customer isn't exist in the database"));
    
            String privateKeyString = privateKeyPem
                .replace(BEGIN_PRIVATE_KEY, "")
                .replaceAll(System.lineSeparator(), "")
                .replace(END_PRIVATE_KEY, "");

            Base64.Decoder decoder = Base64.getDecoder();
    
            byte[] encodedPrivateKey = decoder.decode(privateKeyString);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            byte[] inputBytes = decoder.decode(message.getData());
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] decryptedBytes = cipher.doFinal(inputBytes);
            String decryptedMessageData = new String(decryptedBytes, StandardCharsets.UTF_8);  
            Message decryptedMessage = new Message(message);
            decryptedMessage.setData(decryptedMessageData);
            return decryptedMessage;

        } catch (BadPaddingException | NoSuchAlgorithmException | InvalidKeySpecException | 
                NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException e) {

            throw new MessageDecrypterException(e);
        }
    }

}