package com.scnsoft.bot.logic.crypt;

import java.security.interfaces.RSAPrivateKey;

import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.scnsoft.bot.data.SecretsFileReader;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.exception.MessageDecrypterException;

@Component
public record RsaAlgorithm(
    SecretsFileReader secretsFileReader
) {
    
    public byte[] decrypt(Message message) throws MessageDecrypterException {
        try {
            RSAPrivateKey privateKey = secretsFileReader.readRsaPrivateKey();

            byte[] inputBytes = Base64.decodeBase64(message.getData());
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(inputBytes);

        } catch (Exception e) {
            throw new MessageDecrypterException(e);
        }
    }
    
}
