package com.user.api.controller;

import com.user.api.dto.CadUsuarioRequest;
import com.user.api.dto.CadUsuarioResponse;
import com.user.api.dto.LoginRequest;
import com.user.api.dto.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/login")
public class LoginController {

    @PostMapping
    public ResponseEntity<?>  auth(@Valid @RequestBody LoginRequest loginRequest){
        System.out.println("Email: " + loginRequest.getCusuEmail());
        System.out.println("Senha: " + loginRequest.getCusuSenha());
        return ResponseEntity.ok("Requisição recebida!");
    }
}
