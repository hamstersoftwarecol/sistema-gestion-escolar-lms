package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsModulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LmsModuloRepository extends JpaRepository<LmsModulo, Long> {
    List<LmsModulo> findByCursoIdOrderByOrdenAsc(Long cursoId);

    @Query("SELECT DISTINCT m FROM LmsModulo m LEFT JOIN FETCH m.lecciones WHERE m.curso.id = :cursoId ORDER BY m.orden ASC")
    List<LmsModulo> findModulosConLeccionesPorCursoId(@Param("cursoId") Long cursoId);
}
