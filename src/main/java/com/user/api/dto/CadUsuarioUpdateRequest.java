package com.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CadUsuarioUpdateRequest {

    @NotBlank
    private String cusuNome;

    @NotBlank
    private String cusuLogin;

    @NotBlank
    @Email
    private String cusuEmail;

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

    public String getCusuEmail() {
        return cusuEmail;
    }

    public void setCusuEmail(String cusuEmail) {
        this.cusuEmail = cusuEmail;
    }
}
