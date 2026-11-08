package com.example.demo.lms.service;

import com.example.demo.lms.model.*;
import com.example.demo.lms.repository.*;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class LmsService {

    private final LmsCursoRepository cursoRepository;
    private final LmsModuloRepository moduloRepository;
    private final LmsLeccionRepository leccionRepository;
    private final LmsInscripcionRepository inscripcionRepository;
    private final LmsProgresoLeccionRepository progresoRepository;
    private final LmsResenaRepository resenaRepository;
    private final LmsDebateRepository debateRepository;
    private final LmsAcademiaConfigRepository academiaConfigRepository;
    private final LmsTareaEntregaRepository tareaEntregaRepository;
    private final UsuarioRepository usuarioRepository;

    public LmsService(LmsCursoRepository cursoRepository,
                      LmsModuloRepository moduloRepository,
                      LmsLeccionRepository leccionRepository,
                      LmsInscripcionRepository inscripcionRepository,
                      LmsProgresoLeccionRepository progresoRepository,
                      LmsResenaRepository resenaRepository,
                      LmsDebateRepository debateRepository,
                      LmsAcademiaConfigRepository academiaConfigRepository,
                      LmsTareaEntregaRepository tareaEntregaRepository,
                      UsuarioRepository usuarioRepository) {
        this.cursoRepository = cursoRepository;
        this.moduloRepository = moduloRepository;
        this.leccionRepository = leccionRepository;
        this.inscripcionRepository = inscripcionRepository;
        this.progresoRepository = progresoRepository;
        this.resenaRepository = resenaRepository;
        this.debateRepository = debateRepository;
        this.academiaConfigRepository = academiaConfigRepository;
        this.tareaEntregaRepository = tareaEntregaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // 1. Cursos
    @Transactional(readOnly = true)
    public List<LmsCurso> listarCursosPublicados() {
        return cursoRepository.findByPublicadoTrue();
    }

    @Transactional(readOnly = true)
    public List<LmsCurso> buscarCursos(String categoria, String query) {
        if (query != null && !query.isBlank()) {
            return cursoRepository.buscarPorTexto(query.trim());
        }
        if (categoria != null && !categoria.isBlank() && !"Todas".equalsIgnoreCase(categoria)) {
            return cursoRepository.findByCategoriaIgnoreCase(categoria.trim());
        }
        return cursoRepository.findByPublicadoTrue();
    }

    @Transactional(readOnly = true)
    public List<String> obtenerCategorias() {
        return cursoRepository.findCategoriasDistintas();
    }

    @Transactional(readOnly = true)
    public Optional<LmsCurso> buscarCursoPorId(Long id) {
        return cursoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<LmsCurso> listarCursosPorInstructor(Long instructorId) {
        return cursoRepository.findByInstructorId(instructorId);
    }

    public LmsCurso guardarCurso(LmsCurso curso) {
        return cursoRepository.save(curso);
    }

    // 2. Creador de Cursos (Módulos & Lecciones)
    public LmsModulo agregarModulo(Long cursoId, String titulo, Integer orden) {
        LmsCurso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        int numOrden = (orden != null) ? orden : (moduloRepository.findByCursoIdOrderByOrdenAsc(cursoId).size() + 1);
        LmsModulo modulo = new LmsModulo(curso, titulo, numOrden);
        return moduloRepository.save(modulo);
    }

    public LmsLeccion agregarLeccion(Long moduloId, String titulo, TipoLeccion tipo, String contenido,
                                     String videoUrl, Integer duracionMinutos, String archivoNombre, String archivoUrl) {
        return agregarLeccionCompleta(moduloId, titulo, tipo, contenido, videoUrl, duracionMinutos,
                archivoNombre, archivoUrl, false, 0, null, null, null, null, "A", null, null);
    }

    public LmsLeccion agregarLeccionCompleta(Long moduloId, String titulo, TipoLeccion tipo, String contenido,
                                            String videoUrl, Integer duracionMinutos, String archivoNombre, String archivoUrl,
                                            boolean vistaPrevia, Integer diasGoteo,
                                            String quizPregunta, String quizOpcionA, String quizOpcionB, String quizOpcionC,
                                            String quizRespuestaCorrecta, String quizExplicacion,
                                            String tareaInstrucciones) {
        LmsModulo modulo = moduloRepository.findById(moduloId)
                .orElseThrow(() -> new IllegalArgumentException("Módulo no encontrado"));

        int orden = leccionRepository.findByModuloIdOrderByOrdenAsc(moduloId).size() + 1;
        LmsLeccion leccion = new LmsLeccion(modulo, titulo, tipo, contenido, videoUrl,
                duracionMinutos != null ? duracionMinutos : 15, archivoNombre, archivoUrl, orden);
        leccion.setVistaPrevia(vistaPrevia);
        leccion.setDiasGoteo(diasGoteo != null ? diasGoteo : 0);
        leccion.setQuizPregunta(quizPregunta);
        leccion.setQuizOpcionA(quizOpcionA);
        leccion.setQuizOpcionB(quizOpcionB);
        leccion.setQuizOpcionC(quizOpcionC);
        leccion.setQuizRespuestaCorrecta(quizRespuestaCorrecta != null ? quizRespuestaCorrecta : "A");
        leccion.setQuizExplicacion(quizExplicacion);
        leccion.setTareaInstrucciones(tareaInstrucciones);
        return leccionRepository.save(leccion);
    }

    @Transactional(readOnly = true)
    public List<LmsModulo> listarModulosConLecciones(Long cursoId) {
        return moduloRepository.findModulosConLeccionesPorCursoId(cursoId);
    }

    @Transactional(readOnly = true)
    public List<LmsLeccion> listarTodasLeccionesDeCurso(Long cursoId) {
        return leccionRepository.findByModuloCursoIdOrderByModuloOrdenAscOrdenAsc(cursoId);
    }

    // 3. Inscripciones & Monetización
    public LmsInscripcion inscribirOComprarCurso(Long estudianteId, Long cursoId, Double monto, String metodoPago) {
        if (inscripcionRepository.existsByEstudianteIdAndCursoId(estudianteId, cursoId)) {
            return inscripcionRepository.findByEstudianteIdAndCursoId(estudianteId, cursoId).orElseThrow();
        }

        Usuario estudiante = usuarioRepository.findById(estudianteId)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        LmsCurso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        LmsInscripcion inscripcion = new LmsInscripcion(estudiante, curso,
                monto != null ? monto : curso.getPrecio(),
                metodoPago != null ? metodoPago : "Tarjeta de Crédito Online");
        return inscripcionRepository.save(inscripcion);
    }

    @Transactional(readOnly = true)
    public boolean estaInscrito(Long estudianteId, Long cursoId) {
        return inscripcionRepository.existsByEstudianteIdAndCursoId(estudianteId, cursoId);
    }

    @Transactional(readOnly = true)
    public Optional<LmsInscripcion> obtenerInscripcion(Long estudianteId, Long cursoId) {
        return inscripcionRepository.findByEstudianteIdAndCursoId(estudianteId, cursoId);
    }

    @Transactional(readOnly = true)
    public List<LmsInscripcion> listarInscripcionesDeEstudiante(Long estudianteId) {
        return inscripcionRepository.findByEstudianteId(estudianteId);
    }

    // 4. Progreso de Aprendizaje
    public int marcarLeccionCompletada(Long estudianteId, Long cursoId, Long leccionId) {
        LmsInscripcion inscripcion = inscripcionRepository.findByEstudianteIdAndCursoId(estudianteId, cursoId)
                .orElseThrow(() -> new IllegalArgumentException("No estás inscrito en este curso."));

        LmsLeccion leccion = leccionRepository.findById(leccionId)
                .orElseThrow(() -> new IllegalArgumentException("Lección no encontrada."));

        Optional<LmsProgresoLeccion> progresoOpt = progresoRepository.findByInscripcionIdAndLeccionId(inscripcion.getId(), leccionId);
        if (progresoOpt.isEmpty()) {
            progresoRepository.save(new LmsProgresoLeccion(inscripcion, leccion));
        }

        // Recalcular porcentaje de progreso
        long totalLecciones = leccionRepository.findByModuloCursoIdOrderByModuloOrdenAscOrdenAsc(cursoId).size();
        long completadas = progresoRepository.countByInscripcionIdAndCompletadaTrue(inscripcion.getId());

        int porcentaje = totalLecciones > 0 ? (int) Math.round(((double) completadas / totalLecciones) * 100.0) : 0;
        porcentaje = Math.min(100, Math.max(0, porcentaje));
        inscripcion.setPorcentajeProgreso(porcentaje);
        if (porcentaje == 100) {
            inscripcion.setCompletado(true);
            inscripcion.setFechaCompletado(LocalDateTime.now());
        }
        inscripcionRepository.save(inscripcion);

        return porcentaje;
    }

    @Transactional(readOnly = true)
    public Set<Long> obtenerIdsLeccionesCompletadas(Long inscripcionId) {
        List<LmsProgresoLeccion> lista = progresoRepository.findByInscripcionId(inscripcionId);
        Set<Long> set = new HashSet<>();
        for (LmsProgresoLeccion p : lista) {
            if (p.isCompletada()) {
                set.add(p.getLeccion().getId());
            }
        }
        return set;
    }

    // 5. Reseñas & Valoraciones
    public LmsResena agregarResena(Long cursoId, Long estudianteId, Integer calificacion, String comentario) {
        LmsCurso curso = cursoRepository.findById(cursoId).orElseThrow();
        Usuario estudiante = usuarioRepository.findById(estudianteId).orElseThrow();

        LmsResena resena = new LmsResena(curso, estudiante, calificacion, comentario);
        return resenaRepository.save(resena);
    }

    @Transactional(readOnly = true)
    public List<LmsResena> listarResenasDeCurso(Long cursoId) {
        return resenaRepository.findByCursoIdOrderByFechaDesc(cursoId);
    }

    // 6. Debates / Preguntas y Respuestas
    public LmsDebate crearDebate(Long cursoId, Long usuarioId, String titulo, String pregunta) {
        LmsCurso curso = cursoRepository.findById(cursoId).orElseThrow();
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();

        return debateRepository.save(new LmsDebate(curso, usuario, titulo, pregunta));
    }

    public void responderDebate(Long debateId, String respuesta) {
        debateRepository.findById(debateId).ifPresent(d -> {
            d.setRespuesta(respuesta);
            debateRepository.save(d);
        });
    }

    @Transactional(readOnly = true)
    public List<LmsDebate> listarDebatesDeCurso(Long cursoId) {
        return debateRepository.findByCursoIdOrderByFechaPreguntaDesc(cursoId);
    }

    // 7. Configuración de Marca y Academia
    public LmsAcademiaConfig obtenerConfiguracionAcademia() {
        return academiaConfigRepository.findAll().stream().findFirst().orElseGet(() -> {
            LmsAcademiaConfig c = new LmsAcademiaConfig("EduAcademy Pro", "Plataforma Integral de Cursos Online y Aprendizaje Continuo", 15.0);
            return academiaConfigRepository.save(c);
        });
    }

    public LmsAcademiaConfig actualizarConfiguracion(LmsAcademiaConfig config) {
        return academiaConfigRepository.save(config);
    }

    // 8. Evaluaciones (Quiz), Tareas y Contenido de Goteo
    public boolean evaluarQuiz(Long leccionId, String respuestaSeleccionada) {
        LmsLeccion leccion = leccionRepository.findById(leccionId)
                .orElseThrow(() -> new IllegalArgumentException("Lección no encontrada"));
        if (leccion.getQuizRespuestaCorrecta() == null) {
            return true;
        }
        return leccion.getQuizRespuestaCorrecta().trim().equalsIgnoreCase(respuestaSeleccionada != null ? respuestaSeleccionada.trim() : "");
    }

    public LmsTareaEntrega entregarTarea(Long inscripcionId, Long leccionId, String enlaceEntrega, String notas) {
        LmsInscripcion inscripcion = inscripcionRepository.findById(inscripcionId)
                .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
        LmsLeccion leccion = leccionRepository.findById(leccionId)
                .orElseThrow(() -> new IllegalArgumentException("Lección no encontrada"));

        LmsTareaEntrega entrega = tareaEntregaRepository.findByInscripcionIdAndLeccionId(inscripcionId, leccionId)
                .orElse(new LmsTareaEntrega(inscripcion, leccion, enlaceEntrega, notas));

        entrega.setEnlaceEntrega(enlaceEntrega);
        entrega.setNotasEstudiante(notas);
        entrega.setFechaEntrega(LocalDateTime.now());
        entrega.setEstado("ENTREGADA");
        entrega.setCalificacion(100.0); // Calificación automática base por entrega satisfactoria
        return tareaEntregaRepository.save(entrega);
    }

    @Transactional(readOnly = true)
    public Optional<LmsTareaEntrega> obtenerEntrega(Long inscripcionId, Long leccionId) {
        return tareaEntregaRepository.findByInscripcionIdAndLeccionId(inscripcionId, leccionId);
    }

    @Transactional(readOnly = true)
    public boolean esLeccionBloqueadaPorGoteo(LmsLeccion leccion, LmsInscripcion inscripcion) {
        if (leccion == null || leccion.getDiasGoteo() == null || leccion.getDiasGoteo() <= 0) {
            return false;
        }
        if (inscripcion == null) {
            return true;
        }
        long diasTranscurridos = java.time.temporal.ChronoUnit.DAYS.between(inscripcion.getFechaInscripcion(), LocalDateTime.now());
        return diasTranscurridos < leccion.getDiasGoteo();
    }

    @Transactional(readOnly = true)
    public long diasRestantesGoteo(LmsLeccion leccion, LmsInscripcion inscripcion) {
        if (leccion == null || leccion.getDiasGoteo() == null || leccion.getDiasGoteo() <= 0 || inscripcion == null) {
            return 0;
        }
        long diasTranscurridos = java.time.temporal.ChronoUnit.DAYS.between(inscripcion.getFechaInscripcion(), LocalDateTime.now());
        return Math.max(0, leccion.getDiasGoteo() - diasTranscurridos);
    }

    @Transactional(readOnly = true)
    public Map<Integer, Long> calcularDesgloseEstrellas(Long cursoId) {
        Map<Integer, Long> desglose = new HashMap<>();
        for (int i = 1; i <= 5; i++) desglose.put(i, 0L);
        List<LmsResena> resenas = resenaRepository.findByCursoId(cursoId);
        for (LmsResena r : resenas) {
            int cal = r.getCalificacion() != null ? r.getCalificacion() : 5;
            desglose.put(cal, desglose.getOrDefault(cal, 0L) + 1);
        }
        return desglose;
    }

    // 9. Estadísticas
    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasInstructor(Long instructorId) {
        Map<String, Object> stats = new HashMap<>();
        List<LmsCurso> cursos = cursoRepository.findByInstructorId(instructorId);
        List<LmsInscripcion> inscripciones = inscripcionRepository.findByCursoInstructorId(instructorId);

        Double gananciasTotales = inscripcionRepository.sumarGananciasInstructor(instructorId);
        if (gananciasTotales == null) gananciasTotales = 0.0;

        stats.put("totalCursos", cursos.size());
        stats.put("totalEstudiantes", inscripciones.size());
        stats.put("gananciasTotales", gananciasTotales);
        stats.put("cursos", cursos);
        return stats;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> obtenerEstadisticasGlobales() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalCursos", cursoRepository.count());
        stats.put("totalInscripciones", inscripcionRepository.count());
        Double ventas = inscripcionRepository.sumarVentasTotales();
        stats.put("ventasTotales", ventas != null ? ventas : 0.0);
        return stats;
    }
}
