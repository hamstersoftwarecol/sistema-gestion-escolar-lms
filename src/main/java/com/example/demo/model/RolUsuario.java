package com.example.demo.model;

public enum RolUsuario {
    ADMIN("Administrador"),
    PROFESOR("Profesor"),
    ESTUDIANTE("Estudiante");

    private final String etiqueta;

    RolUsuario(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
