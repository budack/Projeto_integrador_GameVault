package com.gamevault.controller;

import com.gamevault.dto.request.LoginRequest;
import com.gamevault.dto.request.UsuarioRequest;
import com.gamevault.dto.response.LoginResponse;
import com.gamevault.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Endpoints publicos (nao precisam de token).
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // POST /auth/login -> 200 com { token, tipo } ou 401
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // POST /auth/registro -> cria um usuario COMUM e ja devolve o token (201)
    @PostMapping("/registro")
    public ResponseEntity<LoginResponse> registrar(@RequestBody @Valid UsuarioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(request));
    }
}
