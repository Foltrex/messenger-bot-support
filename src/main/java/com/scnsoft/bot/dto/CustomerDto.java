package com.scnsoft.bot.dto;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private UUID id;

    @NotEmpty
    private String pk;
}
