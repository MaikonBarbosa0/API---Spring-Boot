package com.user.api.controller;

import com.user.api.dto.CadUsuarioRequest;
import com.user.api.dto.CadUsuarioResponse;
import com.user.api.model.CadUsuario;
import com.user.api.service.CadUsuarioService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class CadUsuarioController {
    private final CadUsuarioService cadUsuarioService;

    public CadUsuarioController(CadUsuarioService cadUsuarioService) {
        this.cadUsuarioService = cadUsuarioService;
    }

    @GetMapping
    public List<CadUsuario> listar() {
        return cadUsuarioService.listar();
    }

    @GetMapping("/{cusuId}")
    public ResponseEntity<CadUsuario> get(@PathVariable Integer cusuId){
        return ResponseEntity.ok(cadUsuarioService.findUser(cusuId));
    }

    @PostMapping
    public CadUsuarioResponse insert(@Valid  @RequestBody CadUsuarioRequest cadUsuarioRequest){
        return cadUsuarioService.insert(cadUsuarioRequest);
    }
    @PutMapping("/{cusuId}")
    public CadUsuario edit( @PathVariable Integer cusuId, @Valid @RequestBody CadUsuario cadUsuario){
        return cadUsuarioService.atualizar(cusuId, cadUsuario);
    }

    @DeleteMapping("/{cusuId}")
    public ResponseEntity<String>  delete(@PathVariable Integer cusuId){
        return cadUsuarioService.delete(cusuId);
    }

}
