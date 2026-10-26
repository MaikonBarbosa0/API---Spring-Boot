package com.user.api.controller;


import com.user.api.dto.LoginRequest;
import com.user.api.dto.LoginResetPassword;
import com.user.api.security.JwtService;
import com.user.api.service.LoginService;
import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginController(AuthenticationManager authenticationManager,JwtService jwtService, LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<?> auth(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getCusuEmail(),loginRequest.getCusuSenha())
        );

        String token = jwtService.gerarToken((org.springframework.security.core.userdetails.UserDetails)
                authentication.getPrincipal());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "type", "Bearer"
        ));
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody LoginResetPassword loginResetPassword) {
        loginService.resetPassword(loginResetPassword);
        return ResponseEntity.ok("Enviaremos uma solicitação para o email informado.");
    }
}