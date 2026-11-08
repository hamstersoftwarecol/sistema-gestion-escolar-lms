package com.example.demo.lms.model;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_inscripciones", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"estudiante_id", "curso_id"})
})
public class LmsInscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Usuario estudiante;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curso_id", nullable = false)
    private LmsCurso curso;

    private LocalDateTime fechaInscripcion = LocalDateTime.now();

    private Double montoPagado = 0.0;

    private String metodoPago = "Tarjeta de Crédito";

    private Integer porcentajeProgreso = 0; // 0 to 100%

    private boolean completado = false;

    private LocalDateTime fechaCompletado;

    public LmsInscripcion() {
    }

    public LmsInscripcion(Usuario estudiante, LmsCurso curso, Double montoPagado, String metodoPago) {
        this.estudiante = estudiante;
        this.curso = curso;
        this.montoPagado = montoPagado;
        this.metodoPago = metodoPago;
        this.fechaInscripcion = LocalDateTime.now();
        this.porcentajeProgreso = 0;
        this.completado = false;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }

    public LmsCurso getCurso() {
        return curso;
    }

    public void setCurso(LmsCurso curso) {
        this.curso = curso;
    }

    public LocalDateTime getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDateTime fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Double getMontoPagado() {
        return montoPagado;
    }

    public void setMontoPagado(Double montoPagado) {
        this.montoPagado = montoPagado;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public Integer getPorcentajeProgreso() {
        return porcentajeProgreso != null ? porcentajeProgreso : 0;
    }

    public void setPorcentajeProgreso(Integer porcentajeProgreso) {
        this.porcentajeProgreso = porcentajeProgreso;
    }

    public boolean isCompletado() {
        return completado;
    }

    public void setCompletado(boolean completado) {
        this.completado = completado;
    }

    public LocalDateTime getFechaCompletado() {
        return fechaCompletado;
    }

    public void setFechaCompletado(LocalDateTime fechaCompletado) {
        this.fechaCompletado = fechaCompletado;
    }
}
