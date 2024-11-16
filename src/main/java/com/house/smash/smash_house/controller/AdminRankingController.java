package com.house.smash.smash_house.controller;

import com.house.smash.smash_house.model.PlayerRanking;
import com.house.smash.smash_house.model.dto.PlayerRankingCreateDTO;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String updatePlayer(@PathVariable Long id,
                               @ModelAttribute PlayerRankingUpdateDTO updateDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            rankingService.updatePlayerStats(id, updateDTO);
            redirectAttributes.addFlashAttribute("success", "Jugador actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el jugador");
        }
        return "redirect:/ranking";
    }

    @GetMapping("/player/new")
    public String showCreateForm(Model model) {
        model.addAttribute("playerDTO", new PlayerRankingCreateDTO());
        return "admin/player-create";
    }

    @PostMapping("/player/create")
    public String createPlayer(@ModelAttribute PlayerRankingCreateDTO playerDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            rankingService.createPlayer(playerDTO);
            redirectAttributes.addFlashAttribute("success", "Jugador creado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al crear el jugador");
        }
        return "redirect:/ranking";
    }

    @PostMapping("/player/{id}/delete")
    public String deletePlayer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            PlayerRanking player = rankingService.getPlayerById(id);
            String nickname = player.getNickname();
            rankingService.deletePlayer(id);
            redirectAttributes.addFlashAttribute("success",
                    "El jugador " + nickname + " ha sido eliminado del ranking.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Hubo un error al intentar eliminar al jugador.");
        }
        return "redirect:/ranking";
    }
}
