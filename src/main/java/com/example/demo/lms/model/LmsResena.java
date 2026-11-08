package com.example.demo.lms.model;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_resenas")
public class LmsResena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private LmsCurso curso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Usuario estudiante;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer calificacion = 5;

    @Column(length = 2000)
    private String comentario;

    private LocalDateTime fecha = LocalDateTime.now();

    public LmsResena() {
    }

    public LmsResena(LmsCurso curso, Usuario estudiante, Integer calificacion, String comentario) {
        this.curso = curso;
        this.estudiante = estudiante;
        this.calificacion = calificacion;
        this.comentario = comentario;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LmsCurso getCurso() {
        return curso;
    }

    public void setCurso(LmsCurso curso) {
        this.curso = curso;
    }

    public Usuario getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
