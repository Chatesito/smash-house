package com.house.smash.smash_house.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "player_rankings")
public class PlayerRanking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private Integer position;

    private Integer points = 0;
    private Integer tournamentsPlayed = 0;
    private Integer tournamentsWon = 0;
    private Integer matchesWon = 0;
    private Integer matchesLost = 0;
    private String mainCharacter;
    private String secondaryCharacter;

    @Column(columnDefinition = "TEXT")
    private String achievements;

    // Método para calcular el win rate
    public double getWinRate() {
        if (matchesWon + matchesLost == 0) return 0.0;
        return (double) matchesWon / (matchesWon + matchesLost) * 100;
    }

    // Método para calcular el win rate de torneos
    public double getTournamentWinRate() {
        if (tournamentsPlayed == 0) return 0.0;
        return (double) tournamentsWon / tournamentsPlayed * 100;
    }
}
