package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsDebate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LmsDebateRepository extends JpaRepository<LmsDebate, Long> {
    List<LmsDebate> findByCursoIdOrderByFechaPreguntaDesc(Long cursoId);
}
