package com.user.api.service;

import com.user.api.dto.LoginResetPassword;
import com.user.api.model.CadUsuario;
import com.user.api.repository.CadUsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final CadUsuarioRepository cadUsuarioRepository;
    private final EmailService emailServiceConsole;

    public LoginService(CadUsuarioRepository cadUsuarioRepository, EmailService emailServiceConsole){
        this.cadUsuarioRepository = cadUsuarioRepository;
        this.emailServiceConsole = emailServiceConsole;
    }



    public void resetPassword(LoginResetPassword loginResetPassword){
        cadUsuarioRepository.findByCusuEmailAndCusuLogin(loginResetPassword.getCusuEmail(),loginResetPassword.getCusuUser())
                .ifPresent(usuario ->emailServiceConsole.enviarLinkReset(usuario.getCusuEmail(),"http://localhost:4200/reset-senha?token=EXEMPLO"));
    }
}
