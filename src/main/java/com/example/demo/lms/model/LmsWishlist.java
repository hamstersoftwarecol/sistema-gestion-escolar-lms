package com.example.demo.lms.model;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_wishlists", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"usuario_id", "curso_id"})
})
public class LmsWishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id", nullable = false)
    private LmsCurso curso;

    private LocalDateTime fechaAgregado = LocalDateTime.now();

    public LmsWishlist() {
    }

    public LmsWishlist(Usuario usuario, LmsCurso curso) {
        this.usuario = usuario;
        this.curso = curso;
        this.fechaAgregado = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LmsCurso getCurso() {
        return curso;
    }

    public void setCurso(LmsCurso curso) {
        this.curso = curso;
    }

    public LocalDateTime getFechaAgregado() {
        return fechaAgregado;
    }

    public void setFechaAgregado(LocalDateTime fechaAgregado) {
        this.fechaAgregado = fechaAgregado;
    }
}
