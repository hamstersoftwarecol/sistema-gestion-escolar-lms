package com.example.demo.repository;

import com.example.demo.model.RolUsuario;
import com.example.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    List<Usuario> findByRol(RolUsuario rol);
    List<Usuario> findByRolAndActivoTrue(RolUsuario rol);
    Optional<Usuario> findByEmail(String email);
    long countByRol(RolUsuario rol);
}
