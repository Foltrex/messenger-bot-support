package com.scnsoft.bot.service;

import java.util.List;

import com.scnsoft.bot.dto.MessageDto;

public interface MessageService {
    List<MessageDto> respondOnBotMessage(MessageDto message);
}
