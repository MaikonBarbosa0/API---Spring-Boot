package com.user.api.enums;

public enum TabUsuario {
    ADMIN(1, "ADMIN"),
    USER(2, "USER");

    private final Integer id;
    private final String role;

    TabUsuario(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public String getRole() {
        return role;
    }

    public static TabUsuario getById(Integer id) {
        for (TabUsuario usuario : TabUsuario.values()) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }

        throw new IllegalArgumentException("Perfil não encontrado: " + id);
    }
}
