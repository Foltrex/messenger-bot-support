package com.scnsoft.bot.service;

import java.util.UUID;

import com.scnsoft.bot.entity.Chat;

public interface ChatService {
    Chat findById(UUID id);
}
