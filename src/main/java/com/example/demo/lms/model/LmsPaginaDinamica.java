package com.example.demo.lms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_paginas_dinamicas")
public class LmsPaginaDinamica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String slug; // e.g. "terminos", "privacidad", "nosotros", "faq"

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(length = 4000)
    private String contenido;

    private String icono = "fa-file-lines";

    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    public LmsPaginaDinamica() {
    }

    public LmsPaginaDinamica(String slug, String titulo, String contenido, String icono) {
        this.slug = slug;
        this.titulo = titulo;
        this.contenido = contenido;
        this.icono = icono;
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
}
