package com.scnsoft.bot.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.scnsoft.bot.data.SecretsFileReader;
import com.scnsoft.bot.entity.Customer;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.repository.MessageRepository;
import com.scnsoft.bot.repository.CustomerRepository;

public record MessageCryptoService(
    MessageRepository messageRepository,
    CustomerRepository customerRepository,
    SecretsFileReader fileReader
) {

    public Message encryptRSA(Message message, String publicKeyToEncrypt, String privateKeyToSign, String nonce) {
        String chatCreatorPrivateKey = fileReader.readRsaPrivateKey();
        
        return null;
    }

    public String decryptRSA(String message, String publicKeyToVerify, String privateKeyToDEcrypt, String nonce) {

        return null;
    }

    public String encryptAES(String message, String key, String nonce) {
        return null;
    }

    public String decryptAES(String message, String key, String nonce) {
        return null;
    }
}
