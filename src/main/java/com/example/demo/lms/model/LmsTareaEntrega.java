package com.example.demo.lms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_tarea_entregas")
public class LmsTareaEntrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscripcion_id", nullable = false)
    private LmsInscripcion inscripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leccion_id", nullable = false)
    private LmsLeccion leccion;

    private LocalDateTime fechaEntrega = LocalDateTime.now();

    private String enlaceEntrega;

    @Column(length = 2000)
    private String notasEstudiante;

    private Double calificacion; // Ej: 100.0

    @Column(length = 2000)
    private String retroalimentacion;

    private String estado = "ENTREGADA"; // ENTREGADA, CALIFICADA

    public LmsTareaEntrega() {
    }

    public LmsTareaEntrega(LmsInscripcion inscripcion, LmsLeccion leccion, String enlaceEntrega, String notasEstudiante) {
        this.inscripcion = inscripcion;
        this.leccion = leccion;
        this.enlaceEntrega = enlaceEntrega;
        this.notasEstudiante = notasEstudiante;
        this.fechaEntrega = LocalDateTime.now();
        this.estado = "ENTREGADA";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LmsInscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(LmsInscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public LmsLeccion getLeccion() {
        return leccion;
    }

    public void setLeccion(LmsLeccion leccion) {
        this.leccion = leccion;
    }

    public LocalDateTime getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(LocalDateTime fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getEnlaceEntrega() {
        return enlaceEntrega;
    }

    public void setEnlaceEntrega(String enlaceEntrega) {
        this.enlaceEntrega = enlaceEntrega;
    }

    public String getNotasEstudiante() {
        return notasEstudiante;
    }

    public void setNotasEstudiante(String notasEstudiante) {
        this.notasEstudiante = notasEstudiante;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
