package com.user.api.service;

import com.user.api.dto.LoginResetPassword;
import com.user.api.repository.CadUsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final CadUsuarioRepository cadUsuarioRepository;

    public LoginService(CadUsuarioRepository cadUsuarioRepository){
        this.cadUsuarioRepository = cadUsuarioRepository;
    }


    public void resetPassword(LoginResetPassword loginResetPassword){
        cadUsuarioRepository.findByCusuEmailAndCusuLogin(loginResetPassword.getCusuEmail(), loginResetPassword.getCusuUser()).orElseThrow(() ->
               new UsernameNotFoundException("Enviaremos uma solicitação para o email informado.")
        );
    }
}
