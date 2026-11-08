package com.example.demo.service;

import com.example.demo.model.Curso;
import com.example.demo.repository.CursoRepository;
import com.example.demo.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CursoService {

    private final CursoRepository cursoRepository;
    private final MatriculaRepository matriculaRepository;

    public CursoService(CursoRepository cursoRepository, MatriculaRepository matriculaRepository) {
        this.cursoRepository = cursoRepository;
        this.matriculaRepository = matriculaRepository;
    }

    @Transactional(readOnly = true)
    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Curso> listarPorProfesor(Long profesorId) {
        return cursoRepository.findByProfesorId(profesorId);
    }

    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long contarInscritos(Long cursoId) {
        return matriculaRepository.countByCursoId(cursoId);
    }

    @Transactional(readOnly = true)
    public int cuposDisponibles(Curso curso) {
        long inscritos = matriculaRepository.countByCursoId(curso.getId());
        return Math.max(0, curso.getCuposMaximos() - (int) inscritos);
    }
}
