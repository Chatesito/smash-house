package com.house.smash.smash_house.service;

import com.house.smash.smash_house.model.PlayerRanking;
import com.house.smash.smash_house.model.dto.PlayerRankingCreateDTO;
import com.house.smash.smash_house.model.dto.PlayerRankingUpdateDTO;
import com.house.smash.smash_house.repository.PlayerRankingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public PlayerRanking updatePlayerStats(Long id, PlayerRankingUpdateDTO updateDTO) {
        PlayerRanking player = getPlayerById(id);

        player.setTournamentsPlayed(updateDTO.getTournamentsPlayed());
        player.setTournamentsWon(updateDTO.getTournamentsWon());
        player.setMatchesWon(updateDTO.getMatchesWon());
        player.setMatchesLost(updateDTO.getMatchesLost());
        player.setMainCharacter(updateDTO.getMainCharacter());
        player.setSecondaryCharacter(updateDTO.getSecondaryCharacter());
        player.setAchievements(updateDTO.getAchievements());

        // Recalcular puntos
        int points = (player.getTournamentsWon() * 100) + (player.getMatchesWon() * 10);
        player.setPoints(points);

        PlayerRanking savedPlayer = playerRankingRepository.save(player);
        updatePositions();
        return savedPlayer;
    }

    private void updatePositions() {
        List<PlayerRanking> players = playerRankingRepository.findAllByOrderByPointsDesc();
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setPosition(i + 1);
            playerRankingRepository.save(players.get(i));
        }
    }

    @Transactional
    public PlayerRanking createPlayer(PlayerRankingCreateDTO createDTO) {
        PlayerRanking player = new PlayerRanking();
        player.setNickname(createDTO.getNickname());
        player.setMainCharacter(createDTO.getMainCharacter());
        player.setSecondaryCharacter(createDTO.getSecondaryCharacter());

        // Valores iniciales
        player.setPoints(0);
        player.setPosition((int) (playerRankingRepository.count() + 1)); // última posición
        player.setTournamentsPlayed(0);
        player.setTournamentsWon(0);
        player.setMatchesWon(0);
        player.setMatchesLost(0);

        return playerRankingRepository.save(player);
    }

    @Transactional
    public void deletePlayer(Long id) {
        PlayerRanking player = getPlayerById(id);
        playerRankingRepository.delete(player);
        // Actualizar posiciones después de eliminar
        updatePositions();
    }
}
