package com.scnsoft.bot.data;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import org.apache.tomcat.util.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import com.scnsoft.bot.exception.MessageDecrypterException;

@Component
public class SecretsFileReader {
    {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String SECRETS_FILE_PATH = "src/main/resources/secrets.txt";

    private static final String UUID_REGEX = String.format("%s-%s-%s-%s-%s", 
        "[0-9a-fA-F]{8}",
        "[0-9a-fA-F]{4}",
        "[0-9a-fA-F]{4}",
        "[0-9a-fA-F]{4}",
        "[0-9a-fA-F]{12}$");

    public RSAPrivateKey readRsaPrivateKey() throws MessageDecrypterException {
        Path path = Paths.get(SECRETS_FILE_PATH);
        try (Stream<String> lines = Files.lines(path)) {

            String privateKey = lines
                .dropWhile(line -> !line.matches(".*BEGIN RSA PRIVATE KEY.*"))
                .skip(1)
                .takeWhile(line -> !line.matches(".*END RSA PRIVATE KEY.*"))
                .map(line -> line.replaceAll(System.lineSeparator(), ""))
                .collect(Collectors.joining(""));
            
            byte[] encoded = Base64.decodeBase64(privateKey);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new MessageDecrypterException(e);
        }
    }

    public RSAPublicKey readRsaPublicKey() throws MessageDecrypterException {
        Path path = Paths.get(SECRETS_FILE_PATH);
        try (Stream<String> lines = Files.lines(path)) {

            String publicKey = lines
                .dropWhile(line -> !line.matches(".*BEGIN PUBLIC KEY.*"))
                .skip(1)
                .takeWhile(line -> !line.matches(".*END PUBLIC KEY.*"))
                .map(line -> line.replaceAll(System.lineSeparator(), ""))
                .collect(Collectors.joining(""));


            byte[] encoded = Base64.decodeBase64(publicKey);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);

        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new MessageDecrypterException(e);
        }
    }

    public UUID readCustomerId() throws MessageDecrypterException {
        Path path = Paths.get(SECRETS_FILE_PATH);
        try {
            String secrets = new String(Files.readAllBytes(path), Charset.defaultCharset());
            Pattern pattern = Pattern.compile(UUID_REGEX);
            return pattern
                .matcher(secrets)
                .results()
                .findFirst()
                .map(MatchResult::group)
                .map(UUID::fromString)
                .orElseThrow(IllegalArgumentException::new);
                
        } catch (IOException e) {
            throw new MessageDecrypterException(e);
        }
    }

}
