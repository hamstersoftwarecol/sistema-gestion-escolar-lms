package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsInscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LmsInscripcionRepository extends JpaRepository<LmsInscripcion, Long> {
    List<LmsInscripcion> findByEstudianteId(Long estudianteId);
    List<LmsInscripcion> findByCursoId(Long cursoId);
    List<LmsInscripcion> findByCursoInstructorId(Long instructorId);
    Optional<LmsInscripcion> findByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);
    boolean existsByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);
    long countByCursoId(Long cursoId);

    @Query("SELECT COALESCE(SUM(i.montoPagado), 0.0) FROM LmsInscripcion i WHERE i.curso.instructor.id = :instructorId")
    Double sumarGananciasInstructor(Long instructorId);

    @Query("SELECT COALESCE(SUM(i.montoPagado), 0.0) FROM LmsInscripcion i")
    Double sumarVentasTotales();
}
