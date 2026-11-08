package com.example.demo.lms.model;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_articulos_blog")
public class LmsArticuloBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 220)
    private String slug;

    @Column(length = 500)
    private String resumen;

    @Column(length = 4000)
    private String contenido;

    private String imagenUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Usuario autor;

    private String categoria = "Tecnología & Educación";

    private LocalDateTime fechaPublicacion = LocalDateTime.now();

    private Integer leidoMinutos = 5;

    private boolean publicado = true;

    public LmsArticuloBlog() {
    }

    public LmsArticuloBlog(String titulo, String slug, String resumen, String contenido, String imagenUrl, Usuario autor, String categoria, Integer leidoMinutos) {
        this.titulo = titulo;
        this.slug = slug;
        this.resumen = resumen;
        this.contenido = contenido;
        this.imagenUrl = imagenUrl;
        this.autor = autor;
        this.categoria = categoria;
        this.leidoMinutos = leidoMinutos;
        this.fechaPublicacion = LocalDateTime.now();
        this.publicado = true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public LocalDateTime getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Integer getLeidoMinutos() {
        return leidoMinutos;
    }

    public void setLeidoMinutos(Integer leidoMinutos) {
        this.leidoMinutos = leidoMinutos;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }
}
