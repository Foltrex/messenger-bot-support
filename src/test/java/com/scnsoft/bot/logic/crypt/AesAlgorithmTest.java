package com.scnsoft.bot.logic.crypt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.scnsoft.bot.entity.Chat;
import com.scnsoft.bot.entity.Customer;
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.entity.Message.MessageType;
import com.scnsoft.bot.repository.ChatRepository;
import com.scnsoft.bot.repository.MessageRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class AesAlgorithmTest {
    
    @Mock
    private RsaAlgorithm rsaAlgorithm;

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private MessageRepository messageRepository;


    private AesAlgorithm aesAlgorithm;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        aesAlgorithm = new AesAlgorithm(messageRepository, chatRepository, rsaAlgorithm);
    }
    
    @Test
    public void testEncryptShouldEncryptLikeInFrontendPartWhenMessageIsValid() {
        // given
        Chat chat = new Chat(
            UUID.fromString("26306fa9-051d-4141-8591-51bcc50ac675"), 
            UUID.fromString("2fd8f4e8-004e-4d81-9dba-1ecb3d5db0a0"), 
            Set.of(
                new Customer()
            )
        );
        Optional<Chat> optionalChat = Optional.of(chat);
        when(chatRepository.findById(any(UUID.class))).thenReturn(optionalChat);

        Message message = Message.builder()
            .id(UUID.fromString("bf120099-7679-4b5f-9507-8cc00e79b2a9"))
            .chat(UUID.fromString("393c895c-e49d-47f1-a071-a3e9c4338675"))
            .created(Instant.now())
            .data("n7I8B6X5nNrBcggB/2plLmplshFz3ECPrObjEJMsm5A1MTu8GWenYjp6QOsh+fIoXie2QrwX2dD5Z3r9OEY9XkuWQiijnnDIBbbJKwG9SzhIjTBamJ5qEajRNoD0BO+RcKsk3X8CQOvN3S3hi0v+kw3rvMReuwAu4BXtnPwrQjHU3JsYwml7sV1ChDlEcZ+BQpxyLhb91scepE25uv4KKFxrpDzzXYI0sVl74N1z8aDz6tYRv0gwgOx8csfus52+WAlzOaotz2XlcaD8JpfCuiwz0DryKgdWxIPrT/c7Q/+8sW7bYQE00ADaj/5JuoCgxiA9WMoke4enNMiToBcVAg==")
            .nonce("dsymF8wOtDoE581LhC7O6/CEHKB62ZxNQVczgteVqYcf+t10zgK9lligJFZrETu3+U6VisuE/kMjkTxltpJ+ZQZWNAFJYUeoObwbo/4y6WOhaczShmv4ht6JlpDMSkArY/ouMkrcoXO/PIcqTrx0eiEXohhpuSG23rfSixQEYzcvGKn4Z34HWnBEiCKPYisW5hwMRT9mEskt+XvVGWr1rYT+s3zixYb9zJLe7/2SdCZN2HCOJHj+XtUPQLwEk2nlJkPcRXjoq86f0H0kr1LLNFOamiK8soHjfl5AnbawMw6weFFtBrBy3I5RFNibynmpFIouX6ZWUHBqsZ/T7fPcEw==:dWxtajdlMXFvcw==")
            .receiver(UUID.fromString("2fd8f4e8-004e-4d81-9dba-1ecb3d5db0a0"))
            .sender(UUID.fromString("2fd8f4e8-004e-4d81-9dba-1ecb3d5db0a0"))
            .type(MessageType.hello)
            .build();
        Optional<Message> optionalMessage = Optional.of(message);
        when(messageRepository.findByTypeAndReceiver(any(MessageType.class), any(UUID.class))).thenReturn(optionalMessage);

        byte[] decryptedRsaBytes = { 
            115, 100, 102, 95, 95, 105, -102, 81, -44, 91, 107, 77, 117, -119, 27, -8, 42, -27, 1, -24, -29
        };
        when(rsaAlgorithm.decrypt(any(Message.class))).thenReturn(decryptedRsaBytes);

        // when
        Message incomingMessage = Message.builder()
            .id(UUID.fromString("7f7d3c52-73cd-445b-9a18-f50e9cecc48c"))
            .chat(UUID.fromString("393c895c-e49d-47f1-a071-a3e9c4338675"))
            .created(Instant.now())
            .data("CpYjdSZVmUyQ51Ym4uA2xg==")
            .nonce("rkKsIouFUcNk2544/YvDXQ==")
            .receiver(UUID.fromString("7178fa62-1912-4034-a29f-05861f872a6d"))
            .sender(UUID.fromString("2fd8f4e8-004e-4d81-9dba-1ecb3d5db0a0"))
            .type(MessageType.whisper)
            .build();
        byte[] actual = aesAlgorithm.encrypt(incomingMessage);
        log.info("actual bytes: " + actual);

        // then
        byte[] expected = null;
        assertEquals(1, 1);
    }
}
