package com.example.demo.service;

import com.example.demo.model.*;
import com.example.demo.repository.CalificacionRepository;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.MatriculaRepository;
import com.example.demo.repository.TransaccionFinancieraRepository;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final CalificacionRepository calificacionRepository;
    private final TransaccionFinancieraRepository transaccionRepository;

    public MatriculaService(MatriculaRepository matriculaRepository,
                            UsuarioRepository usuarioRepository,
                            CursoRepository cursoRepository,
                            CalificacionRepository calificacionRepository,
                            TransaccionFinancieraRepository transaccionRepository) {
        this.matriculaRepository = matriculaRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.calificacionRepository = calificacionRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Transactional(readOnly = true)
    public List<Matricula> listarTodas() {
        return matriculaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Matricula> buscarPorId(Long id) {
        return matriculaRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Matricula> listarPorEstudiante(Long estudianteId) {
        return matriculaRepository.findByEstudianteId(estudianteId);
    }

    @Transactional(readOnly = true)
    public List<Matricula> listarPorCurso(Long cursoId) {
        return matriculaRepository.findByCursoId(cursoId);
    }

    public Matricula matricular(Long estudianteId, Long cursoId) {
        if (matriculaRepository.existsByEstudianteIdAndCursoId(estudianteId, cursoId)) {
            throw new IllegalArgumentException("El estudiante ya se encuentra matriculado en este curso.");
        }

        Usuario estudiante = usuarioRepository.findById(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        long totalInscritos = matriculaRepository.countByCursoId(cursoId);
        if (totalInscritos >= curso.getCuposMaximos()) {
            throw new IllegalStateException("No hay cupos disponibles en este curso.");
        }

        String codigoMatricula = "MAT-" + LocalDate.now().getYear() + "-" + (matriculaRepository.count() + 101);
        Matricula matricula = new Matricula(codigoMatricula, estudiante, curso, LocalDate.now(), EstadoMatricula.ACTIVA);
        Matricula guardada = matriculaRepository.save(matricula);

        // Inicializar registro de calificaciones
        Calificacion calificacion = new Calificacion(guardada, 0.0, 0.0, 0.0, "Matrícula recién creada");
        calificacionRepository.save(calificacion);

        // Generar orden de cobro financiero de matrícula
        String facturaCodigo = "FAC-" + LocalDate.now().getYear() + "-" + (transaccionRepository.count() + 1001);
        TransaccionFinanciera cobro = new TransaccionFinanciera(
                facturaCodigo,
                estudiante,
                "Matrícula: " + curso.getNombre() + " (" + curso.getCodigo() + ")",
                curso.getCostoMatricula(),
                LocalDate.now(),
                LocalDate.now().plusDays(15),
                null,
                null,
                EstadoFinanciero.PENDIENTE
        );
        transaccionRepository.save(cobro);

        return guardada;
    }

    public void cambiarEstado(Long id, EstadoMatricula nuevoEstado) {
        matriculaRepository.findById(id).ifPresent(m -> {
            m.setEstado(nuevoEstado);
            matriculaRepository.save(m);
        });
    }

    public void eliminar(Long id) {
        calificacionRepository.findByMatriculaId(id).ifPresent(calificacionRepository::delete);
        matriculaRepository.deleteById(id);
    }
}
