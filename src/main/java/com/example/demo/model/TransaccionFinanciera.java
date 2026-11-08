package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

@Entity
@Table(name = "transacciones_financieras")
public class TransaccionFinanciera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String numeroFactura;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Usuario estudiante;

    @NotBlank
    @Column(nullable = false, length = 150)
    private String concepto;

    @NotNull
    @Positive
    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private LocalDate fechaEmision = LocalDate.now();

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    private LocalDate fechaPago;

    private String metodoPago;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoFinanciero estado = EstadoFinanciero.PENDIENTE;

    public TransaccionFinanciera() {
    }

    public TransaccionFinanciera(String numeroFactura, Usuario estudiante, String concepto, Double monto, LocalDate fechaEmision, LocalDate fechaVencimiento, LocalDate fechaPago, String metodoPago, EstadoFinanciero estado) {
        this.numeroFactura = numeroFactura;
        this.estudiante = estudiante;
        this.concepto = concepto;
        this.monto = monto;
        this.fechaEmision = fechaEmision;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaPago = fechaPago;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Usuario getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Usuario estudiante) {
        this.estudiante = estudiante;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public EstadoFinanciero getEstado() {
        return estado;
    }

    public void setEstado(EstadoFinanciero estado) {
        this.estado = estado;
    }
}
