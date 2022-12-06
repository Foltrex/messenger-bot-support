package com.scnsoft.bot.logic.crypt;

import java.security.interfaces.RSAPrivateKey;

import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.scnsoft.bot.dto.MessageDto;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.exception.MessageDecrypterException;
import com.scnsoft.bot.logic.CredentialMapper;

@Component
public record RsaAlgorithm(
    CredentialMapper credentialMapper
) {
    
    public byte[] decrypt(MessageDto messageDto) throws MessageDecrypterException {
        try {
            RSAPrivateKey privateKey = credentialMapper.mapToRsaPrivateKey();

            byte[] inputBytes = Base64.decodeBase64(messageDto.getData());
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(inputBytes);

        } catch (Exception e) {
            throw new MessageDecrypterException(e);
        }
    }
    
}
