package com.example.demo.lms.model;

import jakarta.persistence.*;

@Entity
@Table(name = "lms_academia_config")
public class LmsAcademiaConfig {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreAcademia = "EduAcademy Pro";

    @Column(length = 255)
    private String lema = "Plataforma Integral de Cursos Online y Aprendizaje Continuo";

    private String logoUrl = "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=150&auto=format&fit=crop&q=80";

    private String colorPrimario = "#4f46e5";

    private Double comisionPlataforma = 15.0; // 15%

    private String moneda = "USD";

    private boolean inscripcionesAbiertas = true;

    public LmsAcademiaConfig() {
    }

    public LmsAcademiaConfig(String nombreAcademia, String lema, Double comisionPlataforma) {
        this.nombreAcademia = nombreAcademia;
        this.lema = lema;
        this.comisionPlataforma = comisionPlataforma;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAcademia() {
        return nombreAcademia;
    }

    public void setNombreAcademia(String nombreAcademia) {
        this.nombreAcademia = nombreAcademia;
    }

    public String getLema() {
        return lema;
    }

    public void setLema(String lema) {
        this.lema = lema;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getColorPrimario() {
        return colorPrimario;
    }

    public void setColorPrimario(String colorPrimario) {
        this.colorPrimario = colorPrimario;
    }

    public Double getComisionPlataforma() {
        return comisionPlataforma;
    }

    public void setComisionPlataforma(Double comisionPlataforma) {
        this.comisionPlataforma = comisionPlataforma;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public boolean isInscripcionesAbiertas() {
        return inscripcionesAbiertas;
    }

    public void setInscripcionesAbiertas(boolean inscripcionesAbiertas) {
        this.inscripcionesAbiertas = inscripcionesAbiertas;
    }
}
