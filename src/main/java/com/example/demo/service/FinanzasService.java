package com.example.demo.service;

import com.example.demo.model.EstadoFinanciero;
import com.example.demo.model.TransaccionFinanciera;
import com.example.demo.model.Usuario;
import com.example.demo.repository.TransaccionFinancieraRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class FinanzasService {

    private final TransaccionFinancieraRepository transaccionRepository;
    private final UsuarioRepository usuarioRepository;

    public FinanzasService(TransaccionFinancieraRepository transaccionRepository, UsuarioRepository usuarioRepository) {
        this.transaccionRepository = transaccionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public List<TransaccionFinanciera> listarTodas() {
        return transaccionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<TransaccionFinanciera> listarPorEstudiante(Long estudianteId) {
        return transaccionRepository.findByEstudianteId(estudianteId);
    }

    @Transactional(readOnly = true)
    public Optional<TransaccionFinanciera> buscarPorId(Long id) {
        return transaccionRepository.findById(id);
    }

    public TransaccionFinanciera crearCobro(Long estudianteId, String concepto, Double monto, LocalDate fechaVencimiento) {
        Usuario estudiante = usuarioRepository.findById(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));

        String numeroFactura = "FAC-" + LocalDate.now().getYear() + "-" + (transaccionRepository.count() + 1001);
        TransaccionFinanciera nueva = new TransaccionFinanciera(
                numeroFactura,
                estudiante,
                concepto,
                monto,
                LocalDate.now(),
                fechaVencimiento != null ? fechaVencimiento : LocalDate.now().plusDays(30),
                null,
                null,
                EstadoFinanciero.PENDIENTE
        );
        return transaccionRepository.save(nueva);
    }

    public TransaccionFinanciera registrarPago(Long transaccionId, String metodoPago) {
        TransaccionFinanciera t = transaccionRepository.findById(transaccionId)
                .orElseThrow(() -> new IllegalArgumentException("Transacción no encontrada"));

        t.setEstado(EstadoFinanciero.PAGADO);
        t.setFechaPago(LocalDate.now());
        t.setMetodoPago(metodoPago != null && !metodoPago.isBlank() ? metodoPago : "PSE / En Línea");
        return transaccionRepository.save(t);
    }

    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticas() {
        Map<String, Object> stats = new HashMap<>();
        Double recaudado = transaccionRepository.sumarTotalRecaudado();
        Double pendiente = transaccionRepository.sumarTotalPendiente();
        Double vencido = transaccionRepository.sumarTotalVencido();

        stats.put("totalRecaudado", recaudado != null ? recaudado : 0.0);
        stats.put("totalPendiente", pendiente != null ? pendiente : 0.0);
        stats.put("totalVencido", vencido != null ? vencido : 0.0);
        stats.put("totalGeneral", (recaudado != null ? recaudado : 0.0) + (pendiente != null ? pendiente : 0.0));
        return stats;
    }
}
