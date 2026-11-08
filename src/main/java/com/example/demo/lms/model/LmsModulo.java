package com.example.demo.lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lms_modulos")
public class LmsModulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private LmsCurso curso;

    @NotBlank(message = "El título del módulo es obligatorio")
    @Column(nullable = false, length = 150)
    private String titulo;

    private Integer orden = 1;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<LmsLeccion> lecciones = new ArrayList<>();

    public LmsModulo() {
    }

    public LmsModulo(LmsCurso curso, String titulo, Integer orden) {
        this.curso = curso;
        this.titulo = titulo;
        this.orden = orden;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public List<LmsLeccion> getLecciones() {
        return lecciones;
    }

    public void setLecciones(List<LmsLeccion> lecciones) {
        this.lecciones.clear();
        if (lecciones != null) {
            this.lecciones.addAll(lecciones);
        }
    }
}
