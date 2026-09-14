package com.user.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cad_usuario")
public class CadUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cusu_id")
    private Integer cusuId;

    @Column(name = "cusu_nome")
    private String cusuNome;

    @Column(name = "cusu_login")
    private String cusuLogin;

    @Column(name = "cusu_email")
    private String cusuEmail;

    @Column(name = "cusu_senha")
    private String cusuSenha;

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

    public String getCusuSenha() {
        return cusuSenha;
    }

    public void setCusuSenha(String cusuSenha) {
        this.cusuSenha = cusuSenha;
    }
}
