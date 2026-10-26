package com.user.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginResetPassword {

    @NotBlank
    @Email
    private String cusuEmail;

    @NotBlank
    private String cusuUser;

    public String getCusuEmail() {
        return cusuEmail;
    }

    public void setCusuEmail(String cusuEmail) {
        this.cusuEmail = cusuEmail;
    }

    public String getCusuUser() {
        return cusuUser;
    }

    public void setCusuUser(String cusuUser) {
        this.cusuUser = cusuUser;
    }
}
