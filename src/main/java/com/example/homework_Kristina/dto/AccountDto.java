package com.example.homework_Kristina.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountDto {

    @Schema(example = "Steve Jobs")
    private String name;

    @Pattern(regexp = "^\\+[0-9]{7,15}$")
    private String phoneNr;
}
