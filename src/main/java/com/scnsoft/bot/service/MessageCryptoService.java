package com.scnsoft.bot.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.UUID;
import java.util.Base64;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.kerby.kerberos.kerb.common.PrivateKeyReader;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

import com.scnsoft.bot.data.SecretsFileReader;
import com.scnsoft.bot.entity.Chat;
import com.scnsoft.bot.entity.Customer;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.entity.Utility;
import com.scnsoft.bot.entity.Utility.Key;
import com.scnsoft.bot.exception.MessageDecrypterException;
import com.scnsoft.bot.exception.ServiceException;
import com.scnsoft.bot.logic.decryptor.MessageDecrypter;
import com.scnsoft.bot.logic.decryptor.impl.AesMessageDecrypter;
import com.scnsoft.bot.logic.decryptor.impl.RsaMessageDecrypter;
import com.scnsoft.bot.logic.encrtyper.MessageEncrypter;
import com.scnsoft.bot.logic.encrtyper.impl.AesMessageEncrypter;
import com.scnsoft.bot.logic.encrtyper.impl.RsaMessageEncrypter;
import com.scnsoft.bot.repository.ChatRepository;
import com.scnsoft.bot.repository.CustomerRepository;
import com.scnsoft.bot.repository.MessageRepository;
import com.scnsoft.bot.repository.UtilRepository;

import lombok.extern.log4j.Log4j2;

@Service
public record MessageCryptoService(
    UtilRepository utilRepository,
    ChatRepository chatRepository,
    CustomerRepository customerRepository,
    MessageRepository messageRepository
) {

    public Message decrypt(Message message) throws MessageDecrypterException {
        MessageDecrypter decrypter = switch (message.getType()) {
            case hello -> new RsaMessageDecrypter(utilRepository);
            case whisper -> new AesMessageDecrypter(
                    messageRepository,
                    chatRepository,
                    new RsaMessageDecrypter(utilRepository)
                );
            default -> throw new IllegalArgumentException("Invalid message type for bot");
        };

        return decrypter.decrypt(message);
    }

    public Message encrypt(Message message) {
        MessageEncrypter encrypter = switch (message.getType()) {
            case hello -> new RsaMessageEncrypter();
            case whisper -> new AesMessageEncrypter();
            default -> throw new IllegalArgumentException("Invalid message type for bot");
        };

        return encrypter.encrypt(message);
    }
}
