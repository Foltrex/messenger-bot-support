package com.scnsoft.bot.logic.decrypter;

import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.exception.MessageDecrypterException;

public interface MessageDecrypter {
    byte[] decrypt(Message message) throws MessageDecrypterException;
}
