package com.user.api.service;


import com.user.api.dto.CadUsuarioRequest;
import com.user.api.dto.CadUsuarioResponse;
import com.user.api.exception.CadUsuarioException;
import com.user.api.model.CadUsuario;
import com.user.api.repository.CadUsuarioRepository;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CadUsuarioService {
    private final CadUsuarioRepository cadUsuarioRepository;

    public CadUsuarioService (CadUsuarioRepository cadUsuarioRepository) {
        this.cadUsuarioRepository = cadUsuarioRepository;
    }

    public List<CadUsuario> listar(){
        return cadUsuarioRepository.findAll();
    }

    public CadUsuario findUser(Integer cusuId){
        return cadUsuarioRepository.findById(cusuId).orElseThrow(() -> new CadUsuarioException("User not Found"));
    }

    public CadUsuarioResponse insert(CadUsuarioRequest cadUsuarioRequest) {
        CadUsuario cadUsuario = new CadUsuario();
        cadUsuario.setCusuNome(cadUsuarioRequest.getCusuNome());
        cadUsuario.setCusuLogin(cadUsuarioRequest.getCusuLogin());
        cadUsuario.setCusuSenha(cadUsuarioRequest.getCusuSenha());
        cadUsuario.setCusuEmail(cadUsuarioRequest.getCusuEmail());
        CadUsuario cadUsuarioSalvo = cadUsuarioRepository.save(cadUsuario);
        CadUsuarioResponse cadUsuarioResponse = new CadUsuarioResponse();
        cadUsuarioResponse.setCusuId(cadUsuarioSalvo.getCusuId());
        cadUsuarioResponse.setCusuNome(cadUsuarioSalvo.getCusuNome());
        cadUsuarioResponse.setCusuLogin(cadUsuarioSalvo.getCusuLogin());
        cadUsuarioResponse.setCusuEmail(cadUsuarioSalvo.getCusuEmail());
        return cadUsuarioResponse;
    }

    public CadUsuario atualizar(Integer cusuId, CadUsuario cadUsuario){
        CadUsuario cadUsuarioExistente = findUser(cusuId);
        cadUsuarioExistente.setCusuEmail(cadUsuario.getCusuEmail());
        cadUsuarioExistente.setCusuLogin(cadUsuario.getCusuLogin());
        cadUsuarioExistente.setCusuNome(cadUsuario.getCusuNome());
        cadUsuarioExistente.setCusuSenha(cadUsuario.getCusuSenha());
        return cadUsuarioRepository.save(cadUsuarioExistente);
    }

    public ResponseEntity<String>  delete(Integer cusuId){
       CadUsuario cadUsuarioExistente = findUser(cusuId);

       cadUsuarioRepository.delete(cadUsuarioExistente);
       return ResponseEntity.ok("User deleted sucessful");

    }

}
