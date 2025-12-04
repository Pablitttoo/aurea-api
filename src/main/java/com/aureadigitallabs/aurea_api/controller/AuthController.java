package com.aureadigitallabs.aurea_api.controller;

import com.aureadigitallabs.aurea_api.dto.LoginRequest;
import com.aureadigitallabs.aurea_api.dto.LoginResponse;
import com.aureadigitallabs.aurea_api.model.User;
import com.aureadigitallabs.aurea_api.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // Esto hace que la URL sea .../api/login
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // Llamamos al servicio para verificar usuario y contraseña
        User user = userService.login(request.getUsername(), request.getPassword());

        if (user != null) {
            // Generamos un token "falso" por ahora para simular seguridad
            String token = "Bearer token_simulado_12345";

            // Devolvemos el usuario y el token
            return ResponseEntity.ok(new LoginResponse(user, token));
        }

        // Si el usuario no existe o la contraseña está mal: Error 401
        return ResponseEntity.status(401).build();
    }
}