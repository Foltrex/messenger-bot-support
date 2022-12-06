package com.scnsoft.bot.logic;

import com.scnsoft.bot.model.Message;

public interface MessengerBot {

    Message handleIncommingMessage(Message message);

    String getBotId();

    String getPublicKey();

    String getPrivateKey();
}
