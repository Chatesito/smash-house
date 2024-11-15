package com.house.smash.smash_house.service;

import com.house.smash.smash_house.model.PlayerRanking;
import com.house.smash.smash_house.repository.PlayerRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerRankingService {

    private final PlayerRankingRepository playerRankingRepository;

    public List<PlayerRanking> getAllRankings() {
        return playerRankingRepository.findAllByOrderByPositionAsc();
    }

    public PlayerRanking getPlayerById(Long id) {
        return playerRankingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
    }
}
