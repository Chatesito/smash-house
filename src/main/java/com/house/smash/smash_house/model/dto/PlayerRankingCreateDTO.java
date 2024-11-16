package com.house.smash.smash_house.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PlayerRankingCreateDTO {

    @NotBlank(message = "El nickname es obligatorio")
    private String nickname;
    @NotBlank(message = "El personaje principal es obligatorio")
    private String mainCharacter;
    private String secondaryCharacter;
}
