package com.scnsoft.bot.logic.decrypter.impl;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.tomcat.util.codec.binary.Base64;

import com.scnsoft.bot.data.SecretsFileReader;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.entity.Utility;
import com.scnsoft.bot.exception.MessageDecrypterException;
import com.scnsoft.bot.logic.decrypter.MessageDecrypter;
import com.scnsoft.bot.repository.UtilRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
public record RsaMessageDecrypter(
    SecretsFileReader secretsFileReader
) implements MessageDecrypter {

    /**
     * @return decrypted string in utf-16
     */
    @Override
    public byte[] decrypt(Message message) throws MessageDecrypterException {
        try {
            RSAPrivateKey privateKey = secretsFileReader.readRsaPrivateKey();

            byte[] inputBytes = Base64.decodeBase64(message.getData());
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            return cipher.doFinal(inputBytes);

        } catch (BadPaddingException | NoSuchAlgorithmException | NoSuchPaddingException | 
                InvalidKeyException | IllegalBlockSizeException e) {

            throw new MessageDecrypterException(e);
        }
    }

}