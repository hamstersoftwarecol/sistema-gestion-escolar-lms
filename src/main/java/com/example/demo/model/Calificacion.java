package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "calificaciones")
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matricula_id", nullable = false, unique = true)
    private Matricula matricula;

    private Double nota1 = 0.0;
    private Double nota2 = 0.0;
    private Double notaFinal = 0.0;
    private Double promedio = 0.0;

    private String observaciones;

    public Calificacion() {
    }

    public Calificacion(Matricula matricula, Double nota1, Double nota2, Double notaFinal, String observaciones) {
        this.matricula = matricula;
        this.nota1 = nota1 != null ? nota1 : 0.0;
        this.nota2 = nota2 != null ? nota2 : 0.0;
        this.notaFinal = notaFinal != null ? notaFinal : 0.0;
        this.observaciones = observaciones;
        recalcularPromedio();
    }

    public void recalcularPromedio() {
        double n1 = nota1 != null ? nota1 : 0.0;
        double n2 = nota2 != null ? nota2 : 0.0;
        double nf = notaFinal != null ? notaFinal : 0.0;
        this.promedio = Math.round((n1 * 0.3 + n2 * 0.3 + nf * 0.4) * 100.0) / 100.0;
    }

    public boolean isAprobado() {
        return promedio != null && promedio >= 3.0;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
        recalcularPromedio();
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
        recalcularPromedio();
    }

    public Double getNotaFinal() {
        return notaFinal;
    }

    public void setNotaFinal(Double notaFinal) {
        this.notaFinal = notaFinal;
        recalcularPromedio();
    }

    public Double getPromedio() {
        if (promedio == null || promedio == 0.0) {
            recalcularPromedio();
        }
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
