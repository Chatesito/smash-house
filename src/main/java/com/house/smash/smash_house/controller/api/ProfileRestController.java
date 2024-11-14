package com.house.smash.smash_house.controller.api;

import com.house.smash.smash_house.model.User;
import com.house.smash.smash_house.security.dto.ProfileResponse;
import com.house.smash.smash_house.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class ProfileRestController {

    private final UserService userService;

    public ProfileRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileInfo(Principal principal) {
        try {
            User user = userService.getUserByNickname(principal.getName())
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

            ProfileResponse response = new ProfileResponse(
                    user.getNickname(),
                    user.getName(),
                    user.getEmail()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Error al obtener el perfil: " + e.getMessage());
        }
    }
}
