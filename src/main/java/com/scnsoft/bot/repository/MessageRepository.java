package com.scnsoft.bot.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scnsoft.bot.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    Optional<Message> findByTypeAndReceiver(Message.MessageType type, UUID reciever);
}
