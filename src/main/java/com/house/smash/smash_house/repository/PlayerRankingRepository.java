package com.house.smash.smash_house.repository;

import com.house.smash.smash_house.model.PlayerRanking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayerRankingRepository extends JpaRepository<PlayerRanking, Long> {

    List<PlayerRanking> findAllByOrderByPositionAsc();
    List<PlayerRanking> findAllByOrderByPointsDesc();
}
