package com.example.demo.model;

public enum EstadoMatricula {
    ACTIVA("Activa"),
    PENDIENTE("Pendiente"),
    CANCELADA("Cancelada"),
    FINALIZADA("Finalizada");

    private final String etiqueta;

    EstadoMatricula(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
