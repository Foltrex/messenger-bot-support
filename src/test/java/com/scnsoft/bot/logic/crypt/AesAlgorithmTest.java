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
import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.entity.Message.MessageType;
import com.scnsoft.bot.repository.ChatRepository;
import com.scnsoft.bot.repository.MessageRepository;

@SpringBootTest
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
            UUID.fromString(null), 
            UUID.fromString(null), 
            Set.of()
        );
        Optional<Chat> optionalChat = Optional.of(chat);
        when(chatRepository.findById(any(UUID.class))).thenReturn(optionalChat);

        Message message = Message.builder()
            .id(UUID.fromString("e44dcfa7-6bff-4b41-97e8-82e7b24d7172"))
            .chat(UUID.fromString("26306fa9-051d-4141-8591-51bcc50ac675"))
            .created(Instant.now())
            .data("AgmSzOV1sBt3L4GqqYrTm15Ys3YJBJ+S0pLBb7JCzrT9O4IQWws3avkkrO/8iBD0SOhQsipl2Me55QDeqhJFVV6VxFJ6r2cyMw0tJhXO6U7/bPKvFOQeQqylfDeFgKUwF4BmtgYtU49W3rbPqbsjEkDlkZl7ouLAqTRVCdxxBIFw6Ck48dsjfIQ+eb6Qgqje2MgsWyqsY3I1sdLz+g0SK+NGRoJQVgbt9QMaW6VelQLU1ec9J1zLStOqJvInFHZ73yFtwZWbFHdDsDFfATzetYWDww7A6OznkhgV+MB5ac1Or7N3fhhGfHo/1uehh0wdO7C908AQYMDklR+K+AC3Lw==")
            .nonce("fCQz+FE54CfSAqHZykwohZqV56fb+8rzVby/PZ8qPjxs3rjEoGsHvgPSjpAYhSS/l0LDKbn2JRIAbXIcddkVwzxEMeHLsn+mj8y86DTFs30mvQ/NsitPihLCIqLNvsm3+xdbnE9gy17rcljg+8iJAcr1lVOZCFKo1c/5AL90CfHyJiOVp+MVibAT8uTIIArNvXz+uobsibYVtaqCnO7GkIVSY/8VuDUfUYd1UfNOPV19OgU2qudcHCFh0kvXvVppFWMh31fjXw02NjcNwABoTBTOrGAtoheOzLWzq1iSY8kyt34HuSwIUjguExun6wcWWrkjz9nMO8HC/LVh/d3/mg==:cWV6YjcyOXh4Yg==")
            .receiver(UUID.fromString("0208a7c9-990e-42c3-8f7f-dbc95adb1507"))
            .sender(UUID.fromString("0208a7c9-990e-42c3-8f7f-dbc95adb1507"))
            .type(MessageType.hello)
            .build();
        Optional<Message> optionalMessage = Optional.of(message);
        when(messageRepository.findByTypeAndReceiver(any(MessageType.class), any(UUID.class))).thenReturn(optionalMessage);

        byte[] decryptedRsaBytes = { 
            114, 111, 111, 109, 95, 95, -4, -36, 67, -92, 67, -55, -34, 73, -24, -55, -65, 71, -25, 104, -46, 74
        };
        when(rsaAlgorithm.decrypt(any(Message.class))).thenReturn(decryptedRsaBytes);

        // when
        Message incomingMessage = Message.builder()
            .id(UUID.fromString("0c78a2fc-8418-43f7-a09d-414d17da81bb"))
            .chat(UUID.fromString("26306fa9-051d-4141-8591-51bcc50ac675"))
            .created(Instant.now())
            .data("I4W3tbA8EQuL9+3eR+KseQ==")
            .nonce("Iki0f8M/StR/0J2qei98Hg==")
            .receiver(UUID.fromString("97681130-eb16-4a92-a24a-182556d7f895"))
            .sender(UUID.fromString("0208a7c9-990e-42c3-8f7f-dbc95adb1507"))
            .type(MessageType.whisper)
            .build();
        byte[] actual = aesAlgorithm.encrypt(incomingMessage);

        // then
        byte[] expected = null;
        assertEquals(1, actual);
    }
}
