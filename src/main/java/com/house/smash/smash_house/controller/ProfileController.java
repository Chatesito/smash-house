package com.house.smash.smash_house.controller;

import com.house.smash.smash_house.model.User;
import com.house.smash.smash_house.security.dto.ProfileResponse;
import com.house.smash.smash_house.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String profile(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getUserByNickname(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            model.addAttribute("user", user);
        }
        return "profile";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Validar que las contraseñas nuevas coincidan
            if (!newPassword.equals(confirmPassword)) {
                redirectAttributes.addFlashAttribute("error",
                        "La nueva contraseña y su confirmación no coinciden");
                return "redirect:/profile";
            }

            // Validar longitud mínima
            if (newPassword.length() < 6) {
                redirectAttributes.addFlashAttribute("error",
                        "La contraseña debe tener al menos 6 caracteres");
                return "redirect:/profile";
            }

            // Intentar cambiar la contraseña
            userService.changePassword(principal.getName(), currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("success",
                    "Contraseña actualizada correctamente");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Error al cambiar la contraseña: " + e.getMessage());
        }

        return "redirect:/profile";
    }
}