package com.example.demo.lms.model;

import com.example.demo.model.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "lms_retiros_instructor")
public class LmsRetiroInstructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instructor_id", nullable = false)
    private Usuario instructor;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false, length = 50)
    private String metodoPago; // "Transferencia Bancaria", "PayPal", "Stripe"

    @Column(nullable = false, length = 255)
    private String detallesCuenta; // IBAN, Email de PayPal o Cuenta

    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    private LocalDateTime fechaProcesado;

    @Column(nullable = false, length = 20)
    private String estado = "PENDIENTE"; // PENDIENTE, APROBADO, RECHAZADO

    public LmsRetiroInstructor() {
    }

    public LmsRetiroInstructor(Usuario instructor, Double monto, String metodoPago, String detallesCuenta) {
        this.instructor = instructor;
        this.monto = monto;
        this.metodoPago = metodoPago;
        this.detallesCuenta = detallesCuenta;
        this.fechaSolicitud = LocalDateTime.now();
        this.estado = "PENDIENTE";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getInstructor() {
        return instructor;
    }

    public void setInstructor(Usuario instructor) {
        this.instructor = instructor;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getDetallesCuenta() {
        return detallesCuenta;
    }

    public void setDetallesCuenta(String detallesCuenta) {
        this.detallesCuenta = detallesCuenta;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDateTime getFechaProcesado() {
        return fechaProcesado;
    }

    public void setFechaProcesado(LocalDateTime fechaProcesado) {
        this.fechaProcesado = fechaProcesado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
