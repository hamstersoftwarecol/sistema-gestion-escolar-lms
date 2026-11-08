package com.example.demo.repository;

import com.example.demo.model.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Long> {
    Optional<Calificacion> findByMatriculaId(Long matriculaId);
    List<Calificacion> findByMatriculaEstudianteId(Long estudianteId);
    List<Calificacion> findByMatriculaCursoId(Long cursoId);
}
