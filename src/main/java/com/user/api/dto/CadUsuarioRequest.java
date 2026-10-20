package com.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CadUsuarioRequest {

    @NotBlank
    private String cusuNome;

    @NotBlank
    private String cusuLogin;

    @Size(min = 6,message = "Por favor coloque no minimo 6 letras na senha")
    @NotBlank
    private String cusuSenha;

    @Email(message = "Por favor insira um email valido")
    @NotBlank
    private String cusuEmail;

    @NotNull
    private Integer cusuTusuId;

    public String getCusuNome() {
        return cusuNome;
    }

    public void setCusuNome(String cusuNome) {
        this.cusuNome = cusuNome;
    }

    public String getCusuLogin() {
        return cusuLogin;
    }

    public void setCusuLogin(String cusuLogin) {
        this.cusuLogin = cusuLogin;
    }

    public String getCusuSenha() {
        return cusuSenha;
    }

    public void setCusuSenha(String cusuSenha) {
        this.cusuSenha = cusuSenha;
    }

    public String getCusuEmail() {
        return cusuEmail;
    }

    public void setCusuEmail(String cusuEmail) {
        this.cusuEmail = cusuEmail;
    }

    public Integer getCusuTusuId() {
        return cusuTusuId;
    }

    public void setCusuTusuId(Integer cusuTusuId) {
        this.cusuTusuId = cusuTusuId;
    }
}
