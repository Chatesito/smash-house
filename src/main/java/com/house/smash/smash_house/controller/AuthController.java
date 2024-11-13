package com.house.smash.smash_house.controller;

import com.house.smash.smash_house.model.Role;
import com.house.smash.smash_house.model.User;
import com.house.smash.smash_house.model.dto.RegisterDTO;
import com.house.smash.smash_house.service.RoleService;
import com.house.smash.smash_house.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.HashSet;

@Controller
@RequestMapping
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;

    private AuthController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registerDTO", new RegisterDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO,
                               BindingResult result,
                               Model model) {

        // Validar que las contraseñas coincidan
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword",
                    "Las contraseñas no coinciden");
            return "register";
        }

        // Verificar si el nickname ya existe
        if (userService.getUserByNickname(registerDTO.getNickname()).isPresent()) {
            result.rejectValue("nickname", "error.nickname",
                    "Este nickname ya está en uso");
            return "register";
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {
            // Crear nuevo usuario
            User user = new User();
            user.setName(registerDTO.getName());
            user.setEmail(registerDTO.getEmail());
            user.setNickname(registerDTO.getNickname());
            user.setPassword(registerDTO.getPassword());

            // Asignar rol USER por defecto
            Role userRole = roleService.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado."));

            user.setRoles(new HashSet<>(Collections.singletonList(userRole)));

            userService.registerUser(user);

            return "redirect:/login?success";
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            result.rejectValue("global", "error.global",
                    "Error al registrar el usuario. Por favor, intente nuevamente.");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
