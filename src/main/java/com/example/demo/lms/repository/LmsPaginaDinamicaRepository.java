package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsPaginaDinamica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LmsPaginaDinamicaRepository extends JpaRepository<LmsPaginaDinamica, Long> {
    Optional<LmsPaginaDinamica> findBySlug(String slug);
}
