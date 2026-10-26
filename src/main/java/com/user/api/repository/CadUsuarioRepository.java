package com.user.api.repository;

import com.user.api.dto.CadUsuarioResponse;
import com.user.api.model.CadUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.Optional;

public interface CadUsuarioRepository extends JpaRepository<CadUsuario, Integer> {
    Optional<CadUsuario> findByCusuLogin(String cusuLogin);
    Optional<CadUsuario> findByCusuEmail(String cusuEmail);
    Optional<CadUsuario> findByCusuEmailAndCusuLogin(String cusuEmail, String cusuLogin);

    @Query("""
            SELECT new com.user.api.dto.CadUsuarioResponse(
                    u.cusuId,
                    u.cusuNome,
                    u.cusuLogin,
                    u.cusuEmail
                )
                FROM CadUsuario u
            
            """)
    ArrayList<CadUsuarioResponse> listarUsuarios();
}
