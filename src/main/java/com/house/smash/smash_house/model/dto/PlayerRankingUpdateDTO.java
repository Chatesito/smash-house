package com.house.smash.smash_house.model.dto;

import lombok.Data;

@Data
public class PlayerRankingUpdateDTO {

    private Integer tournamentsPlayed;
    private Integer tournamentsWon;
    private Integer matchesWon;
    private Integer matchesLost;
    private String achievements;
}
