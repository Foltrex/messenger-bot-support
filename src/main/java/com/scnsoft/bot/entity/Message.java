package com.scnsoft.bot.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {

    public enum MessageType {
        hello, //0
        iam, //1
        whisper, //2
        who, //3
        server,
        LEAVE_CHAT,
        ASSIGN_ROLE,
    }

    private UUID id;

    private UUID sender;

    private UUID receiver;

    private UUID chat;

    private MessageType type;

    private String data;

    private String attachments;

    private String nonce;

    private Instant created;
}

