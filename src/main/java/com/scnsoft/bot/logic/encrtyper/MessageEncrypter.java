package com.scnsoft.bot.logic.encrtyper;

import com.scnsoft.bot.entity.Message;

public interface MessageEncrypter {
    Message encrypt(Message message);
}
