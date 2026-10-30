package com.user.api.service;

import com.user.api.security.UsuarioAutenticado;
import com.user.api.model.CadUsuario;
import com.user.api.repository.CadUsuarioRepository;
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        CadUsuario usuario = cadUsuarioRepository
                .findByCusuEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado")
                );

        return new UsuarioAutenticado(usuario);
    }

    public UsuarioAutenticado buscarPorId(Integer id) {
        return new UsuarioAutenticado(cadUsuarioRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado")));
    }
}
