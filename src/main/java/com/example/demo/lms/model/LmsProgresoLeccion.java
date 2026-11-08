package com.example.demo.lms.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_progreso_lecciones", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"inscripcion_id", "leccion_id"})
})
public class LmsProgresoLeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inscripcion_id", nullable = false)
    private LmsInscripcion inscripcion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "leccion_id", nullable = false)
    private LmsLeccion leccion;

    private boolean completada = true;

    private LocalDateTime fechaCompletada = LocalDateTime.now();

    public LmsProgresoLeccion() {
    }

    public LmsProgresoLeccion(LmsInscripcion inscripcion, LmsLeccion leccion) {
        this.inscripcion = inscripcion;
        this.leccion = leccion;
        this.completada = true;
        this.fechaCompletada = LocalDateTime.now();
    }

    // Getters y Setters
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

    public boolean isCompletada() {
        return completada;
    }

    public void setCompletada(boolean completada) {
        this.completada = completada;
    }

    public LocalDateTime getFechaCompletada() {
        return fechaCompletada;
    }

    public void setFechaCompletada(LocalDateTime fechaCompletada) {
        this.fechaCompletada = fechaCompletada;
    }
}
