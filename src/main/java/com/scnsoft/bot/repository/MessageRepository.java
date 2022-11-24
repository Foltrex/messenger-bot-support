package com.scnsoft.bot.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scnsoft.bot.entity.Message;

public interface MessageRepository extends JpaRepository<Message, UUID> {
    Optional<Message> findByTypeAndReceiver(Message.MessageType type, UUID reciever);
}
