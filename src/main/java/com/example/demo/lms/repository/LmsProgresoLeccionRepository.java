package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsProgresoLeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LmsProgresoLeccionRepository extends JpaRepository<LmsProgresoLeccion, Long> {
    List<LmsProgresoLeccion> findByInscripcionId(Long inscripcionId);
    Optional<LmsProgresoLeccion> findByInscripcionIdAndLeccionId(Long inscripcionId, Long leccionId);
    long countByInscripcionIdAndCompletadaTrue(Long inscripcionId);
}
