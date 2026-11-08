package com.example.demo.repository;

import com.example.demo.model.EstadoMatricula;
import com.example.demo.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByEstudianteId(Long estudianteId);
    List<Matricula> findByCursoId(Long cursoId);
    List<Matricula> findByCursoProfesorId(Long profesorId);
    List<Matricula> findByEstado(EstadoMatricula estado);
    long countByCursoId(Long cursoId);
    boolean existsByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);
}
