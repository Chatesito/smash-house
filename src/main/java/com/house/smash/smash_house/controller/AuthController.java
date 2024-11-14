package com.house.smash.smash_house.controller;

import com.house.smash.smash_house.model.Role;
import com.house.smash.smash_house.model.User;
import com.house.smash.smash_house.model.dto.RegisterDTO;
import com.house.smash.smash_house.security.dto.JwtResponse;
import com.house.smash.smash_house.security.dto.LoginRequest;
import com.house.smash.smash_house.security.jwt.JwtUtil;
import com.house.smash.smash_house.service.RoleService;
import com.house.smash.smash_house.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.HashSet;

@Controller
@RequestMapping
public class AuthController {

    private final UserService userService;
    private final RoleService roleService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService,
                          RoleService roleService,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userService = userService;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // Endpoints
    @GetMapping("/auth")
    public String showAuthForm(Model model) {
        if (!model.containsAttribute("registerDTO")) {
            model.addAttribute("registerDTO", new RegisterDTO());
        }
        return "auth";
    }

    // Procesamiento del registro
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registerDTO") RegisterDTO registerDTO,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        // Validar que las contraseñas coincidan
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            result.rejectValue("confirmPassword", "error.confirmPassword",
                    "Las contraseñas no coinciden");
            return "auth";
        }

        // Verificar si el nickname ya existe
        if (userService.getUserByNickname(registerDTO.getNickname()).isPresent()) {
            result.rejectValue("nickname", "error.nickname",
                    "Este nickname ya está en uso");
            return "auth"; // Cambiar a auth
        }

        if (result.hasErrors()) {
            return "auth";
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

            redirectAttributes.addFlashAttribute("registrationSuccess",
                    "Registro exitoso. Por favor, inicia sesión.");
            return "redirect:/auth";
        } catch (Exception e) {
            e.printStackTrace();
            result.rejectValue("global", "error.global",
                    "Error al registrar el usuario. Por favor, intente nuevamente.");
            return "auth";
        }
    }

    // Endpoints REST para el JWT
    @PostMapping("/api/auth/login")
    @ResponseBody  // Importante para retornar JSON en vez de una vista
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getNickname(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(loginRequest.getNickname());

        User user = userService.getUserByNickname(loginRequest.getNickname())
                .orElseThrow(() -> new RuntimeException("Error: Usuario no encontrado."));

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                user.getNickname(),
                user.getName(),
                user.getEmail()
        ));
    }

    // Opcionalmente, puedes añadir un endpoint para registro vía API
    @PostMapping("/api/auth/register")
    @ResponseBody
    public ResponseEntity<?> registerUserApi(@Valid @RequestBody RegisterDTO registerDTO) {
        try {
            if (userService.getUserByNickname(registerDTO.getNickname()).isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body("Error: Nickname ya está en uso!");
            }

            User user = new User();
            user.setName(registerDTO.getName());
            user.setEmail(registerDTO.getEmail());
            user.setNickname(registerDTO.getNickname());
            user.setPassword(registerDTO.getPassword());

            Role userRole = roleService.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado."));

            user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            userService.registerUser(user);

            return ResponseEntity.ok("Usuario registrado exitosamente!");
        } catch (Exception e) {
            return ResponseEntity
                    .internalServerError()
                    .body("Error al registrar el usuario: " + e.getMessage());
        }
    }
}
