package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsTareaEntrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface LmsTareaEntregaRepository extends JpaRepository<LmsTareaEntrega, Long> {
    Optional<LmsTareaEntrega> findByInscripcionIdAndLeccionId(Long inscripcionId, Long leccionId);
    List<LmsTareaEntrega> findByLeccionId(Long leccionId);
}
