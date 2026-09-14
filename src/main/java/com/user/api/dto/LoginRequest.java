package com.user.api.dto;

public class LoginRequest {

    private String cusuEmail = null;
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
