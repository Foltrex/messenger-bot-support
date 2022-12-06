package com.scnsoft.bot.logic;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import com.scnsoft.bot.exception.InvalidBotCredentialsException;

@Component
public record CredentialMapper(MessengerBot messengerBot) {
    private static final String ALGORITHM = "RSA";

    private static final String RSA_PRIVATE_KEY_REGEX = "(?<=-----BEGIN RSA PRIVATE KEY-----(\r\n?|\n))((.*)\1)+(?=-----END RSA PRIVATE KEY-----)";

    private static final String RSA_PUBLIC_KEY_REGEX = "(?<=-----BEGIN PUBLIC KEY-----(\r\n?|\n))((.*)\1)+(?=-----END PUBLIC KEY-----)";


    public UUID mapToUUID() {
        return UUID.fromString(messengerBot.getBotId());
    }
    
    public RSAPrivateKey mapToRsaPrivateKey() throws InvalidBotCredentialsException {
        try {
            Pattern pattern = Pattern.compile(RSA_PRIVATE_KEY_REGEX);
            String privateKeyStringWithoutHeaderAndFooter = pattern
                .matcher(messengerBot.getPrivateKey())
                .results()
                .findFirst()
                .map(MatchResult::group)
                .orElseThrow(InvalidBotCredentialsException::new);

            String privateKeyStringWithoutLineSeparators = privateKeyStringWithoutHeaderAndFooter
                .replaceAll(System.lineSeparator(), "");

            byte[] decodedPrivateKey = Base64.decodeBase64(privateKeyStringWithoutLineSeparators);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKey);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InvalidBotCredentialsException(e);
        }
    }

    public RSAPublicKey mapToRsaPublicKey(String publicKeyString) throws InvalidBotCredentialsException {
        try {
            Pattern pattern = Pattern.compile(RSA_PUBLIC_KEY_REGEX);
            String publicKeyStringWithoutHeaderAndFooter = pattern
                .matcher(messengerBot.getPublicKey())
                .results()
                .findFirst()
                .map(MatchResult::group)
                .orElseThrow(InvalidBotCredentialsException::new);

            String publicKeyStringWithoutLineSeparators = publicKeyStringWithoutHeaderAndFooter
                .replaceAll(System.lineSeparator(), "");

            byte[] decodedPrivateKey = Base64.decodeBase64(publicKeyStringWithoutLineSeparators);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedPrivateKey);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InvalidBotCredentialsException(e);
        }
    }
}
