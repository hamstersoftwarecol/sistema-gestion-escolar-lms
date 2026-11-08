package com.example.demo.lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "lms_lecciones")
public class LmsLeccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "modulo_id", nullable = false)
    private LmsModulo modulo;

    @NotBlank(message = "El título de la lección es obligatorio")
    @Column(nullable = false, length = 150)
    private String titulo;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoLeccion tipo = TipoLeccion.VIDEO;

    @Column(length = 4000)
    private String contenido;

    private String videoUrl = "https://www.youtube.com/embed/dQw4w9WgXcQ";

    private Integer duracionMinutos = 15;

    private String archivoAdjuntoNombre;

    private String archivoAdjuntoUrl;

    private Integer orden = 1;

    // Vista previa gratuita para visitantes sin comprar
    private boolean vistaPrevia = false;

    // Contenido de goteo: días tras inscripción requeridos para desbloqueo (0 = inmediato)
    private Integer diasGoteo = 0;

    // Cuestionarios y evaluaciones
    @Column(length = 1000)
    private String quizPregunta;

    @Column(length = 500)
    private String quizOpcionA;

    @Column(length = 500)
    private String quizOpcionB;

    @Column(length = 500)
    private String quizOpcionC;

    @Column(length = 10)
    private String quizRespuestaCorrecta = "A";

    @Column(length = 1000)
    private String quizExplicacion;

    // Tareas prácticas e instrucciones
    @Column(length = 2000)
    private String tareaInstrucciones;

    public LmsLeccion() {
    }

    public LmsLeccion(LmsModulo modulo, String titulo, TipoLeccion tipo, String contenido, String videoUrl, Integer duracionMinutos, String archivoAdjuntoNombre, String archivoAdjuntoUrl, Integer orden) {
        this.modulo = modulo;
        this.titulo = titulo;
        this.tipo = tipo;
        this.contenido = contenido;
        this.videoUrl = videoUrl;
        this.duracionMinutos = duracionMinutos;
        this.archivoAdjuntoNombre = archivoAdjuntoNombre;
        this.archivoAdjuntoUrl = archivoAdjuntoUrl;
        this.orden = orden;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LmsModulo getModulo() {
        return modulo;
    }

    public void setModulo(LmsModulo modulo) {
        this.modulo = modulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoLeccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoLeccion tipo) {
        this.tipo = tipo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getDuracionMinutos() {
        return duracionMinutos;
    }

    public void setDuracionMinutos(Integer duracionMinutos) {
        this.duracionMinutos = duracionMinutos;
    }

    public String getArchivoAdjuntoNombre() {
        return archivoAdjuntoNombre;
    }

    public void setArchivoAdjuntoNombre(String archivoAdjuntoNombre) {
        this.archivoAdjuntoNombre = archivoAdjuntoNombre;
    }

    public String getArchivoAdjuntoUrl() {
        return archivoAdjuntoUrl;
    }

    public void setArchivoAdjuntoUrl(String archivoAdjuntoUrl) {
        this.archivoAdjuntoUrl = archivoAdjuntoUrl;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public boolean isVistaPrevia() {
        return vistaPrevia;
    }

    public void setVistaPrevia(boolean vistaPrevia) {
        this.vistaPrevia = vistaPrevia;
    }

    public Integer getDiasGoteo() {
        return diasGoteo;
    }

    public void setDiasGoteo(Integer diasGoteo) {
        this.diasGoteo = diasGoteo;
    }

    public String getQuizPregunta() {
        return quizPregunta;
    }

    public void setQuizPregunta(String quizPregunta) {
        this.quizPregunta = quizPregunta;
    }

    public String getQuizOpcionA() {
        return quizOpcionA;
    }

    public void setQuizOpcionA(String quizOpcionA) {
        this.quizOpcionA = quizOpcionA;
    }

    public String getQuizOpcionB() {
        return quizOpcionB;
    }

    public void setQuizOpcionB(String quizOpcionB) {
        this.quizOpcionB = quizOpcionB;
    }

    public String getQuizOpcionC() {
        return quizOpcionC;
    }

    public void setQuizOpcionC(String quizOpcionC) {
        this.quizOpcionC = quizOpcionC;
    }

    public String getQuizRespuestaCorrecta() {
        return quizRespuestaCorrecta;
    }

    public void setQuizRespuestaCorrecta(String quizRespuestaCorrecta) {
        this.quizRespuestaCorrecta = quizRespuestaCorrecta;
    }

    public String getQuizExplicacion() {
        return quizExplicacion;
    }

    public void setQuizExplicacion(String quizExplicacion) {
        this.quizExplicacion = quizExplicacion;
    }

    public String getTareaInstrucciones() {
        return tareaInstrucciones;
    }

    public void setTareaInstrucciones(String tareaInstrucciones) {
        this.tareaInstrucciones = tareaInstrucciones;
    }
}
