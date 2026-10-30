package com.user.api.security;

import com.user.api.enums.TabUsuario;
import com.user.api.model.CadUsuario;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public class UsuarioAutenticado extends User {
    private final Integer id;

    public UsuarioAutenticado(CadUsuario usuario) {
        super(usuario.getCusuEmail(), usuario.getCusuSenha(),
                List.of(new SimpleGrantedAuthority(
                        "ROLE_" + TabUsuario.getById(usuario.getCusuTusuId()).getRole())));
        this.id = usuario.getCusuId();
    }

    public Integer getId() {
        return id;
    }
}
