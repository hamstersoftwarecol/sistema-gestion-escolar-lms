package com.example.demo.lms.model;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_archivos_media")
public class LmsArchivoMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String tipo; // "IMAGEN", "DOCUMENTO", "VIDEO"

    @Column(nullable = false, length = 500)
    private String url;

    private Long tamanoKb = 250L;

    private LocalDateTime fechaSubida = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subido_por_id")
    private Usuario subidoPor;

    public LmsArchivoMedia() {
    }

    public LmsArchivoMedia(String nombre, String tipo, String url, Long tamanoKb, Usuario subidoPor) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.url = url;
        this.tamanoKb = tamanoKb;
        this.subidoPor = subidoPor;
        this.fechaSubida = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getTamanoKb() {
        return tamanoKb;
    }

    public void setTamanoKb(Long tamanoKb) {
        this.tamanoKb = tamanoKb;
    }

    public LocalDateTime getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDateTime fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public Usuario getSubidoPor() {
        return subidoPor;
    }

    public void setSubidoPor(Usuario subidoPor) {
        this.subidoPor = subidoPor;
    }
}
