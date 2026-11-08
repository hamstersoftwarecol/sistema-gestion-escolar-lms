package com.example.demo.service;

import com.example.demo.model.Calificacion;
import com.example.demo.repository.CalificacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;

    public CalificacionService(CalificacionRepository calificacionRepository) {
        this.calificacionRepository = calificacionRepository;
    }

    @Transactional(readOnly = true)
    public List<Calificacion> listarPorEstudiante(Long estudianteId) {
        return calificacionRepository.findByMatriculaEstudianteId(estudianteId);
    }

    @Transactional(readOnly = true)
    public List<Calificacion> listarPorCurso(Long cursoId) {
        return calificacionRepository.findByMatriculaCursoId(cursoId);
    }

    @Transactional(readOnly = true)
    public Optional<Calificacion> buscarPorMatricula(Long matriculaId) {
        return calificacionRepository.findByMatriculaId(matriculaId);
    }

    @Transactional(readOnly = true)
    public Optional<Calificacion> buscarPorId(Long id) {
        return calificacionRepository.findById(id);
    }

    public Calificacion actualizarNotas(Long calificacionId, Double nota1, Double nota2, Double notaFinal, String observaciones) {
        Calificacion cal = calificacionRepository.findById(calificacionId)
                .orElseThrow(() -> new IllegalArgumentException("Calificación no encontrada"));

        cal.setNota1(nota1);
        cal.setNota2(nota2);
        cal.setNotaFinal(notaFinal);
        cal.setObservaciones(observaciones);
        cal.recalcularPromedio();

        return calificacionRepository.save(cal);
    }
}
