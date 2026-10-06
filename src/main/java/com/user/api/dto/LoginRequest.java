package com.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @Email
    @NotBlank
    private String cusuEmail = null;

    @Size(min = 6)
    @NotBlank
    private String cusuSenha = null;

    public String getCusuEmail() {
        return cusuEmail;
    }

    public void setCusuEmail(String cusuEmail) {
        this.cusuEmail = cusuEmail;
    }

    public String getCusuSenha() {
        return cusuSenha;
    }

    public void setCusuSenha(String cusuSenha) {
        this.cusuSenha = cusuSenha;
    }
}
