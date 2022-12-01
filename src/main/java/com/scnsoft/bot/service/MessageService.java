package com.scnsoft.bot.service;

import java.util.List;

import com.scnsoft.bot.entity.Message;

public interface MessageService {
    List<Message> respondOnBotMessage(Message message);
}
