package io.github.matheusthoffmann.xadrezweb.controller;

import io.github.matheusthoffmann.xadrezweb.dto.auth.LoginRequest;
import io.github.matheusthoffmann.xadrezweb.dto.auth.LoginResponse;
import io.github.matheusthoffmann.xadrezweb.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }
}