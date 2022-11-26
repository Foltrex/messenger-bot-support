package com.scnsoft.bot.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scnsoft.bot.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, UUID> {
    
}
