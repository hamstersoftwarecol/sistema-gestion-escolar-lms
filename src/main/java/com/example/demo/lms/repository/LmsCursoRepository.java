package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsCurso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LmsCursoRepository extends JpaRepository<LmsCurso, Long> {
    List<LmsCurso> findByPublicadoTrue();
    List<LmsCurso> findByInstructorId(Long instructorId);
    List<LmsCurso> findByCategoriaIgnoreCase(String categoria);

    @Query("SELECT c FROM LmsCurso c WHERE c.publicado = true AND LOWER(c.titulo) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<LmsCurso> buscarPorTexto(@Param("query") String query);

    @Query("SELECT DISTINCT c.categoria FROM LmsCurso c WHERE c.publicado = true")
    List<String> findCategoriasDistintas();
}
