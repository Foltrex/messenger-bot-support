package com.scnsoft.bot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scnsoft.bot.entity.Message;
import com.scnsoft.bot.service.MessageCryptoService;

@RestController
@RequestMapping("/bot/messages")
public record MessageContoller(MessageCryptoService messageService) {

    @PostMapping
    public Message save(@RequestBody Message message) {
        return null;
    }
}
