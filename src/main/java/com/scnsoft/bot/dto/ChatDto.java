package com.scnsoft.bot.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {

    private UUID id;

    private UUID creatorId;

    private Set<CustomerDto> members = new HashSet<>();
}

