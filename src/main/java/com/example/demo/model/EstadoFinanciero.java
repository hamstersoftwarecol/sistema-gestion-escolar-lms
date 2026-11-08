package com.example.demo.model;

public enum EstadoFinanciero {
    PAGADO("Pagado"),
    PENDIENTE("Pendiente"),
    VENCIDO("Vencido");

    private final String etiqueta;

    EstadoFinanciero(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getEtiqueta() {
        return etiqueta;
    }
}
