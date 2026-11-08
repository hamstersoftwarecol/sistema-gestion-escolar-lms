package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsLeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LmsLeccionRepository extends JpaRepository<LmsLeccion, Long> {
    List<LmsLeccion> findByModuloIdOrderByOrdenAsc(Long moduloId);
    List<LmsLeccion> findByModuloCursoIdOrderByModuloOrdenAscOrdenAsc(Long cursoId);
}
