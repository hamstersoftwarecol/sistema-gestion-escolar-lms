package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El código es obligatorio")
    @Column(nullable = false, unique = true, length = 30)
    private String codigo;

    @NotBlank(message = "El nombre de la materia/curso es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El grado o nivel es obligatorio")
    @Column(nullable = false, length = 50)
    private String grado;

    @Column(length = 30)
    private String periodoAcademico = "2026-I";

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profesor_id")
    private Usuario profesor;

    @NotNull
    @Min(value = 1, message = "Debe haber al menos 1 cupo")
    private Integer cuposMaximos = 35;

    @NotNull
    @PositiveOrZero
    private Double costoMatricula = 250.0;

    private String aula;

    private String horario;

    public Curso() {
    }

    public Curso(String codigo, String nombre, String grado, String periodoAcademico, Usuario profesor, Integer cuposMaximos, Double costoMatricula, String aula, String horario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.grado = grado;
        this.periodoAcademico = periodoAcademico;
        this.profesor = profesor;
        this.cuposMaximos = cuposMaximos;
        this.costoMatricula = costoMatricula;
        this.aula = aula;
        this.horario = horario;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getPeriodoAcademico() {
        return periodoAcademico;
    }

    public void setPeriodoAcademico(String periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    public Usuario getProfesor() {
        return profesor;
    }

    public void setProfesor(Usuario profesor) {
        this.profesor = profesor;
    }

    public Integer getCuposMaximos() {
        return cuposMaximos;
    }

    public void setCuposMaximos(Integer cuposMaximos) {
        this.cuposMaximos = cuposMaximos;
    }

    public Double getCostoMatricula() {
        return costoMatricula;
    }

    public void setCostoMatricula(Double costoMatricula) {
        this.costoMatricula = costoMatricula;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
