package com.scnsoft.bot.logic.decryptor;

import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.exception.MessageDecrypterException;

public interface MessageDecrypter {
    Message decrypt(Message message) throws MessageDecrypterException;
}
