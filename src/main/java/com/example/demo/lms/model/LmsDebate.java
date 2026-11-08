package com.example.demo.lms.model;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_debates")
public class LmsDebate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private LmsCurso curso;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(length = 3000, nullable = false)
    private String pregunta;

    @Column(length = 3000)
    private String respuesta;

    private LocalDateTime fechaPregunta = LocalDateTime.now();

    private LocalDateTime fechaRespuesta;

    public LmsDebate() {
    }

    public LmsDebate(LmsCurso curso, Usuario usuario, String titulo, String pregunta) {
        this.curso = curso;
        this.usuario = usuario;
        this.titulo = titulo;
        this.pregunta = pregunta;
        this.fechaPregunta = LocalDateTime.now();
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
        this.fechaRespuesta = LocalDateTime.now();
    }

    public LocalDateTime getFechaPregunta() {
        return fechaPregunta;
    }

    public void setFechaPregunta(LocalDateTime fechaPregunta) {
        this.fechaPregunta = fechaPregunta;
    }

    public LocalDateTime getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(LocalDateTime fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }
}
