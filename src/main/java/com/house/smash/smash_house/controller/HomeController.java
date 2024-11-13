package com.house.smash.smash_house.controller;

import com.house.smash.smash_house.model.User;
import com.house.smash.smash_house.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
public class HomeController {

    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getUserByNickname(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            model.addAttribute("user", user);
        }
        return "home";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getUserByNickname(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
            model.addAttribute("user", user);
        }
        return "profile";
    }

    @PostMapping("/profile/change-password")
    public String changePassword(@RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 Principal principal,
                                 RedirectAttributes redirectAttributes) {
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Las contraseñas nuevas no coinciden");
            return "redirect:/profile";
        }

        try {
            userService.changePassword(principal.getName(), currentPassword, newPassword);
            redirectAttributes.addFlashAttribute("success", "Contraseña actualizada correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al cambiar la contraseña: " + e.getMessage());
        }

        return "redirect:/profile";
    }
}
