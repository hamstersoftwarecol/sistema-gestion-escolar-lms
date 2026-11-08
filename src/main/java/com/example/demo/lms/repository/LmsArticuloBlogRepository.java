package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsArticuloBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LmsArticuloBlogRepository extends JpaRepository<LmsArticuloBlog, Long> {
    List<LmsArticuloBlog> findByPublicadoTrueOrderByFechaPublicacionDesc();
    List<LmsArticuloBlog> findByCategoriaAndPublicadoTrueOrderByFechaPublicacionDesc(String categoria);
    Optional<LmsArticuloBlog> findBySlug(String slug);
}
