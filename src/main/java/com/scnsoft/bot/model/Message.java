package com.scnsoft.bot.model;

import lombok.Builder;

public record Message(
    String data,
    String attachments
) {
}
