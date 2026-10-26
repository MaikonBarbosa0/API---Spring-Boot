package com.user.api.dto;

public class CadUsuarioResponse {


    private Integer cusuId;
    private String cusuNome;
    private String cusuLogin;
    private String cusuEmail;

    public CadUsuarioResponse(){}

    public CadUsuarioResponse(Integer cusuId, String cusuNome, String cusuLogin, String cusuEmail) {
        this.cusuId = cusuId;
        this.cusuNome = cusuNome;
        this.cusuLogin = cusuLogin;
        this.cusuEmail = cusuEmail;
    }

    public Integer getCusuId() {
        return cusuId;
    }

    public void setCusuId(Integer cusuId) {
        this.cusuId = cusuId;
    }

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
