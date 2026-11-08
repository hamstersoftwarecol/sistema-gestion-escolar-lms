package com.example.demo.repository;

import com.example.demo.model.EstadoFinanciero;
import com.example.demo.model.TransaccionFinanciera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionFinancieraRepository extends JpaRepository<TransaccionFinanciera, Long> {
    List<TransaccionFinanciera> findByEstudianteId(Long estudianteId);
    List<TransaccionFinanciera> findByEstado(EstadoFinanciero estado);
    List<TransaccionFinanciera> findTop10ByOrderByFechaEmisionDesc();

    @Query("SELECT COALESCE(SUM(t.monto), 0.0) FROM TransaccionFinanciera t WHERE t.estado = com.example.demo.model.EstadoFinanciero.PAGADO")
    Double sumarTotalRecaudado();

    @Query("SELECT COALESCE(SUM(t.monto), 0.0) FROM TransaccionFinanciera t WHERE t.estado = com.example.demo.model.EstadoFinanciero.PENDIENTE")
    Double sumarTotalPendiente();

    @Query("SELECT COALESCE(SUM(t.monto), 0.0) FROM TransaccionFinanciera t WHERE t.estado = com.example.demo.model.EstadoFinanciero.VENCIDO")
    Double sumarTotalVencido();
}
