package com.house.smash.smash_house.controller;

import com.house.smash.smash_house.model.dto.PlayerRankingUpdateDTO;
import com.house.smash.smash_house.service.PlayerRankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminRankingController {

    private final PlayerRankingService rankingService;

    @GetMapping("/player/{id}")
    public String showPlayerAdmin(@PathVariable Long id, Model model) {
        model.addAttribute("player", rankingService.getPlayerById(id));
        return "admin/player-details-admin";
    }

    @PostMapping("/player/{id}/update")
    public String updatePlayer(@PathVariable Long id, @ModelAttribute PlayerRankingUpdateDTO updateDTO) {
        rankingService.updatePlayerStats(id, updateDTO);
        return "redirect:/ranking";
    }
}
