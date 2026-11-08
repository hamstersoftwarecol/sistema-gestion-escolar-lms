package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsAcademiaConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LmsAcademiaConfigRepository extends JpaRepository<LmsAcademiaConfig, Long> {
}
