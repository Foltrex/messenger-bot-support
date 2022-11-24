package com.scnsoft.bot.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Component;

import com.scnsoft.bot.exception.SecretsFileReaderException;

@Component
public class SecretsFileReader {
    private static final String RSA_PRIVATE_KEY_FILE_PATH = "src/main/resources/rsa_private_key.txt";

    public String readRsaPrivateKey() throws SecretsFileReaderException {
        Path path = Paths.get(RSA_PRIVATE_KEY_FILE_PATH);
        try (Stream<String> lines = Files.lines(path)) {

            return lines
                .filter(line -> !line.matches(".*RSA PRIVATE KEY.*"))
                .collect(Collectors.joining("\n"));

        } catch (IOException e) {
            throw new SecretsFileReaderException(e);
        }
    }

}
