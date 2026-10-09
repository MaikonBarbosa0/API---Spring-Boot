package com.user.api.service;

import com.user.api.model.CadUsuario;
import com.user.api.repository.CadUsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final CadUsuarioRepository cadUsuarioRepository;

    public CustomUserDetailsService(CadUsuarioRepository cadUsuarioRepository) {
        this.cadUsuarioRepository = cadUsuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        CadUsuario usuario = cadUsuarioRepository
                .findByCusuLogin(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado")
                );

        return User.withUsername(usuario.getCusuLogin()).password(usuario.getCusuSenha()).roles("USER").build();
    }
}
