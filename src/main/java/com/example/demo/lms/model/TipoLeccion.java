package com.example.demo.lms.model;

public enum TipoLeccion {
    VIDEO("Video"),
    CONFERENCIA("Conferencia"),
    ARTICULO("Lectura"),
    QUIZ("Cuestionario"),
    TAREA("Tarea Práctica");

    private final String etiqueta;

    TipoLeccion(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
