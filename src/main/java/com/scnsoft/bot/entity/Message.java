package com.scnsoft.bot.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
// import javax.validation.constraints.NotNull;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
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

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Type(type = "org.hibernate.type.UUIDCharType")
    // @NotNull
    private UUID sender;

    @Type(type = "org.hibernate.type.UUIDCharType")
    // @NotNull
    private UUID receiver;

    @Type(type = "org.hibernate.type.UUIDCharType")
    // @NotNull
    private UUID chat;

    @Enumerated(EnumType.ORDINAL)
    // @NotNull
    private MessageType type;

    @Column(length = 344)
    private String data;

    private String attachments;

    @Column(length = 361)
    private String nonce;

    private Instant created;

    public Message(Message message) {
        this.sender = message.getSender();
        this.receiver = message.getReceiver();
        this.chat = message.getChat();
        this.type = message.getType();
        this.data = message.getData();
        this.attachments = message.getAttachments();
        this.nonce = message.getNonce();
        this.created = message.getCreated();
    }
}