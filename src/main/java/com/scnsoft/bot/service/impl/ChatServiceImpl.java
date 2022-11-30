package com.scnsoft.bot.service.impl;

import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.scnsoft.bot.entity.Chat;
import com.scnsoft.bot.repository.ChatRepository;
import com.scnsoft.bot.service.ChatService;

@Service
public record ChatServiceImpl(
    ChatRepository chatRepository
) implements ChatService {

    @Override
    public Chat findById(UUID id) {
        return chatRepository
            .findById(id)
            .orElseThrow(NoSuchElementException::new);
    }
        
}
