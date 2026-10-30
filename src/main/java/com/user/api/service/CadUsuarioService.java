package com.user.api.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.api.dto.CadUsuarioRequest;
import com.user.api.dto.CadUsuarioResponse;
import com.user.api.dto.CadUsuarioUpdateRequest;
import com.user.api.enums.TabUsuario;
import com.user.api.exception.CadUsuarioException;
import com.user.api.model.CadUsuario;
import com.user.api.repository.CadUsuarioRepository;
import com.user.api.security.UsuarioAutenticado;

@Service
public class CadUsuarioService {
    private final CadUsuarioRepository cadUsuarioRepository;
    private final PasswordEncoder passwordEncoder;


    public CadUsuarioService (CadUsuarioRepository cadUsuarioRepository,  PasswordEncoder passwordEncoder) {
        this.cadUsuarioRepository = cadUsuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public ArrayList<CadUsuarioResponse> listRespons() {
        return cadUsuarioRepository.listarUsuarios();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<CadUsuarioResponse> listar(){
        List<CadUsuario> usuarios = cadUsuarioRepository.findAll();
        List<CadUsuarioResponse> respostas = new ArrayList<>();

        for (CadUsuario usuario : usuarios) {
            respostas.add(toResponse(usuario));
        }

        return respostas;
    }

    public CadUsuario findUser(Integer cusuId){
        CadUsuario usuario = cadUsuarioRepository.findById(cusuId)
                .orElseThrow(() -> new CadUsuarioException("User not Found"));
        validarAcesso(usuario);
        return usuario;
    }

    private void validarAcesso(CadUsuario usuario) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Acesso negado.");
        }

        boolean administrador = authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority()
                        .equals("ROLE_" + TabUsuario.ADMIN.getRole()));

        boolean proprioUsuario = authentication.getPrincipal() instanceof UsuarioAutenticado autenticado
                && usuario.getCusuId().equals(autenticado.getId());

        if (!administrador && !proprioUsuario) {
            throw new AccessDeniedException("Acesso negado.");
        }
    }

    public CadUsuarioResponse insert(CadUsuarioRequest cadUsuarioRequest) {
        CadUsuario cadUsuario = new CadUsuario();
        cadUsuario.setCusuNome(cadUsuarioRequest.getCusuNome());
        cadUsuario.setCusuLogin(cadUsuarioRequest.getCusuLogin());
        cadUsuario.setCusuSenha(passwordEncoder.encode(cadUsuarioRequest.getCusuSenha()));
        cadUsuario.setCusuEmail(cadUsuarioRequest.getCusuEmail());
        cadUsuario.setCusuTusuId(TabUsuario.USER.getId());
        CadUsuario cadUsuarioSalvo = cadUsuarioRepository.save(cadUsuario);
        CadUsuarioResponse cadUsuarioResponse = new CadUsuarioResponse();
        cadUsuarioResponse.setCusuId(cadUsuarioSalvo.getCusuId());
        cadUsuarioResponse.setCusuNome(cadUsuarioSalvo.getCusuNome());
        cadUsuarioResponse.setCusuLogin(cadUsuarioSalvo.getCusuLogin());
        cadUsuarioResponse.setCusuEmail(cadUsuarioSalvo.getCusuEmail());
        return cadUsuarioResponse;
    }


    public CadUsuarioResponse toResponse(CadUsuario cadUsuario){

        CadUsuarioResponse response = new CadUsuarioResponse();

        response.setCusuId(cadUsuario.getCusuId());
        response.setCusuNome(cadUsuario.getCusuNome());
        response.setCusuLogin(cadUsuario.getCusuLogin());
        response.setCusuEmail(cadUsuario.getCusuEmail());

        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CadUsuario atualizar(Integer cusuId, CadUsuarioUpdateRequest request){
        CadUsuario cadUsuarioExistente = findUser(cusuId);
        cadUsuarioExistente.setCusuEmail(request.getCusuEmail());
        cadUsuarioExistente.setCusuLogin(request.getCusuLogin());
        cadUsuarioExistente.setCusuNome(request.getCusuNome());

        return cadUsuarioRepository.save(cadUsuarioExistente);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String>  delete(Integer cusuId){
       CadUsuario cadUsuarioExistente = findUser(cusuId);

       cadUsuarioRepository.delete(cadUsuarioExistente);
       return ResponseEntity.ok("User deleted sucessful");

    }

}
