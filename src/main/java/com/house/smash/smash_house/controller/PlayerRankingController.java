package com.house.smash.smash_house.controller;

import com.house.smash.smash_house.service.PlayerRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class PlayerRankingController {

    private final PlayerRankingService playerRankingService;

    @GetMapping("/ranking")
    public String showRanking(Model model) {
        model.addAttribute("players", playerRankingService.getAllRankings());
        return "ranking";
    }

    @GetMapping("/player/{id}")
    public String showPlayerDetails(@PathVariable Long id, Model model) {
        model.addAttribute("player", playerRankingService.getPlayerById(id));
        return "player-details";
    }
}
