package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsResena;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LmsResenaRepository extends JpaRepository<LmsResena, Long> {
    List<LmsResena> findByCursoId(Long cursoId);
    List<LmsResena> findByCursoIdOrderByFechaDesc(Long cursoId);
}
