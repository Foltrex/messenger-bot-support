package com.scnsoft.bot.dto;

import java.time.Instant;
import java.util.UUID;

import com.scnsoft.bot.model.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageDto {
    
    public enum MessageType {
        hello,
        iam,
        whisper,
        who,
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


    public MessageDto(MessageDto messageDto) {
        this.sender = messageDto.sender;
        this.receiver = messageDto.receiver;
        this.chat = messageDto.chat;
        this.type = messageDto.type;
        this.data = messageDto.data;
        this.attachments = messageDto.attachments;
        this.nonce = messageDto.attachments;
        this.created = messageDto.created;
    }

    public Message toMessage() {
        return new Message(data, attachments);
    }
}
