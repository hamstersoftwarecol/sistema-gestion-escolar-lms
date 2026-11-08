package com.example.demo.lms.repository;

import com.example.demo.lms.model.LmsRetiroInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LmsRetiroInstructorRepository extends JpaRepository<LmsRetiroInstructor, Long> {
    List<LmsRetiroInstructor> findByInstructorIdOrderByFechaSolicitudDesc(Long instructorId);
    List<LmsRetiroInstructor> findByEstadoOrderByFechaSolicitudDesc(String estado);
    
    @Query("SELECT COALESCE(SUM(r.monto), 0.0) FROM LmsRetiroInstructor r WHERE r.instructor.id = :instructorId AND r.estado = 'APROBADO'")
    Double sumarRetirosAprobados(@Param("instructorId") Long instructorId);
}
