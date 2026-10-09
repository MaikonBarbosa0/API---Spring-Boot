package com.user.api.repository;

import com.user.api.model.CadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CadUsuarioRepository extends JpaRepository<CadUsuario, Integer> {
    Optional<CadUsuario> findByCusuLogin(String cusuLogin);
}
