package com.user.api.controller;

import com.user.api.dto.CadUsuarioRequest;
import com.user.api.dto.CadUsuarioResponse;
import com.user.api.dto.CadUsuarioUpdateRequest;
import com.user.api.enums.TabUsuario;
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
    public List<CadUsuarioResponse> listar() {
        return cadUsuarioService.listar();
    }

    @GetMapping("/{cusuId}")
    public ResponseEntity<CadUsuarioResponse> get(@PathVariable Integer cusuId){
        CadUsuario usuario = cadUsuarioService.findUser(cusuId);
        return ResponseEntity.ok(cadUsuarioService.toResponse(usuario));
    }

    @PostMapping
    public CadUsuarioResponse insert(@Valid  @RequestBody CadUsuarioRequest cadUsuarioRequest){
        return cadUsuarioService.insert(cadUsuarioRequest);
    }
    @PutMapping("/{cusuId}")
    public CadUsuarioResponse edit( @PathVariable Integer cusuId, @Valid @RequestBody CadUsuarioUpdateRequest request){
        return cadUsuarioService.toResponse(cadUsuarioService.atualizar(cusuId, request));
    }

    @DeleteMapping("/{cusuId}")
    public ResponseEntity<String>  delete(@PathVariable Integer cusuId){
        return cadUsuarioService.delete(cusuId);
    }

}
