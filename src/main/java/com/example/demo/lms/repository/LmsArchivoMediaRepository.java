package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsArchivoMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LmsArchivoMediaRepository extends JpaRepository<LmsArchivoMedia, Long> {
    List<LmsArchivoMedia> findAllByOrderByFechaSubidaDesc();
    List<LmsArchivoMedia> findByTipoOrderByFechaSubidaDesc(String tipo);
}
