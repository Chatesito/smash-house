package com.house.smash.smash_house.controller;

import com.house.smash.smash_house.model.User;
import com.house.smash.smash_house.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
