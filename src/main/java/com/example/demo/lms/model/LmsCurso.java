package com.example.demo.lms.model;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lms_cursos")
public class LmsCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(length = 255)
    private String subtitulo;

    @Column(length = 4000)
    private String descripcion;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Usuario instructor;

    @Column(nullable = false, length = 80)
    private String categoria = "Desarrollo de Software";

    @Column(length = 30)
    private String nivel = "Todos los niveles";

    @NotNull
    private Double precio = 49.99;

    private String imagenUrl = "https://images.unsplash.com/photo-1517694712202-14dd9538aa97?w=800&auto=format&fit=crop&q=80";

    private boolean publicado = true;

    private Integer duracionHoras = 12;

    private LocalDateTime fechaCreacion = LocalDateTime.now();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("orden ASC")
    private List<LmsModulo> modulos = new ArrayList<>();

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LmsResena> resenas = new ArrayList<>();

    public LmsCurso() {
    }

    public LmsCurso(String titulo, String subtitulo, String descripcion, Usuario instructor, String categoria, String nivel, Double precio, String imagenUrl, Integer duracionHoras) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.descripcion = descripcion;
        this.instructor = instructor;
        this.categoria = categoria;
        this.nivel = nivel;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.duracionHoras = duracionHoras;
        this.publicado = true;
        this.fechaCreacion = LocalDateTime.now();
    }

    public double getPromedioRating() {
        if (resenas == null || resenas.isEmpty()) {
            return 5.0;
        }
        double sum = resenas.stream().mapToInt(LmsResena::getCalificacion).sum();
        return Math.round((sum / resenas.size()) * 10.0) / 10.0;
    }

    public int getTotalLecciones() {
        if (modulos == null) return 0;
        return modulos.stream()
                .filter(m -> m.getLecciones() != null)
                .mapToInt(m -> m.getLecciones().size())
                .sum();
    }

    // Getters y Setters
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

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getInstructor() {
        return instructor;
    }

    public void setInstructor(Usuario instructor) {
        this.instructor = instructor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }

    public Integer getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(Integer duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public List<LmsModulo> getModulos() {
        return modulos;
    }

    public void setModulos(List<LmsModulo> modulos) {
        this.modulos.clear();
        if (modulos != null) {
            this.modulos.addAll(modulos);
        }
    }

    public List<LmsResena> getResenas() {
        return resenas;
    }

    public void setResenas(List<LmsResena> resenas) {
        this.resenas.clear();
        if (resenas != null) {
            this.resenas.addAll(resenas);
        }
    }
}
