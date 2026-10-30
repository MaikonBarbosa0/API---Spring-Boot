package com.user.api;

import com.user.api.dto.CadUsuarioUpdateRequest;
import com.user.api.model.CadUsuario;
import com.user.api.repository.CadUsuarioRepository;
import com.user.api.security.JwtService;
import com.user.api.security.UsuarioAutenticado;
import com.user.api.service.CadUsuarioService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioSecurityTests {
    private final CadUsuarioRepository repository = mock(CadUsuarioRepository.class);
    private final CadUsuarioService service = new CadUsuarioService(repository, mock(PasswordEncoder.class));

    @AfterEach
    void limparContexto() {
        SecurityContextHolder.clearContext();
    }

    private CadUsuario usuario(int id, int perfil) {
        CadUsuario usuario = new CadUsuario();
        usuario.setCusuId(id);
        usuario.setCusuEmail("usuario" + id + "@example.com");
        usuario.setCusuSenha("hash-de-teste");
        usuario.setCusuTusuId(perfil);
        return usuario;
    }

    private void autenticar(CadUsuario usuario) {
        UsuarioAutenticado principal = new UsuarioAutenticado(usuario);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities()));
    }

    @Test
    void donoPodeConsultarEAtualizarPreservandoSenhaEPerfil() {
        CadUsuario dono = usuario(10, 2);
        autenticar(dono);
        when(repository.findById(10)).thenReturn(Optional.of(dono));
        when(repository.save(dono)).thenReturn(dono);
        CadUsuarioUpdateRequest request = new CadUsuarioUpdateRequest();
        request.setCusuNome("Novo nome");
        request.setCusuLogin("novo.login");
        request.setCusuEmail("novo@example.com");
        assertSame(dono, service.findUser(10));
        service.atualizar(10, request);
        assertEquals("novo@example.com", dono.getCusuEmail());
        assertEquals("hash-de-teste", dono.getCusuSenha());
        assertEquals(2, dono.getCusuTusuId());
        assertSame(dono, service.findUser(10));
    }

    @Test
    void outroUsuarioNaoPodeConsultarNemAtualizar() {
        CadUsuario alvo = usuario(20, 2);
        autenticar(usuario(10, 2));
        when(repository.findById(20)).thenReturn(Optional.of(alvo));
        CadUsuarioUpdateRequest request = new CadUsuarioUpdateRequest();
        request.setCusuEmail("usuario10@example.com");
        assertThrows(AccessDeniedException.class, () -> service.findUser(20));
        assertThrows(AccessDeniedException.class, () -> service.atualizar(20, request));
        assertEquals("usuario20@example.com", alvo.getCusuEmail());
        verify(repository, never()).save(any());
    }

    @Test
    void administradorPodeConsultarEAtualizarOutroUsuario() {
        CadUsuario alvo = usuario(20, 2);
        autenticar(usuario(1, 1));
        when(repository.findById(20)).thenReturn(Optional.of(alvo));
        when(repository.save(alvo)).thenReturn(alvo);
        assertSame(alvo, service.findUser(20));
        assertSame(alvo, service.atualizar(20, new CadUsuarioUpdateRequest()));
        verify(repository).save(alvo);
    }

    @Test
    void semAutenticacaoNaoPodeConsultar() {
        when(repository.findById(20)).thenReturn(Optional.of(usuario(20, 2)));
        assertThrows(AccessDeniedException.class, () -> service.findUser(20));
    }

    @Test
    void jwtIdentificaIdMesmoAposTrocaDeEmail() {
        JwtService jwt = new JwtService(Base64.getEncoder().encodeToString(new byte[32]));
        CadUsuario dono = usuario(10, 2);
        String token = jwt.gerarToken(new UsuarioAutenticado(dono));
        dono.setCusuEmail("outro@example.com");
        assertEquals(10, jwt.extrairUsuarioId(token));
        assertTrue(jwt.tokenValido(token, new UsuarioAutenticado(dono)));
        assertFalse(jwt.tokenValido(token, new UsuarioAutenticado(usuario(20, 2))));
    }
}
