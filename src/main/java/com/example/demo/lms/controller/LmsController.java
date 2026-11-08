package com.example.demo.lms.controller;

import com.example.demo.lms.model.*;
import com.example.demo.lms.service.LmsService;
import com.example.demo.model.RolUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.security.UsuarioPrincipal;
import com.example.demo.service.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/lms")
public class LmsController {

    private final LmsService lmsService;
    private final UsuarioService usuarioService;

    public LmsController(LmsService lmsService, UsuarioService usuarioService) {
        this.lmsService = lmsService;
        this.usuarioService = usuarioService;
    }

    // 1. Catálogo / Marketplace de Cursos
    @GetMapping
    public String catalogo(@RequestParam(required = false) String categoria,
                           @RequestParam(required = false) String buscar,
                           @AuthenticationPrincipal UsuarioPrincipal principal,
                           Model model) {
        model.addAttribute("cursos", lmsService.buscarCursos(categoria, buscar));
        model.addAttribute("categorias", lmsService.obtenerCategorias());
        model.addAttribute("categoriaSeleccionada", categoria);
        model.addAttribute("busquedaActual", buscar);
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());

        if (principal != null) {
            model.addAttribute("misInscripciones", lmsService.listarInscripcionesDeEstudiante(principal.getId()));
        }

        return "lms/catalogo";
    }

    // 2. Detalle del Curso
    @GetMapping("/curso/{id}")
    public String detalleCurso(@PathVariable Long id,
                               @AuthenticationPrincipal UsuarioPrincipal principal,
                               Model model) {
        LmsCurso curso = lmsService.buscarCursoPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        model.addAttribute("curso", curso);
        model.addAttribute("modulos", lmsService.listarModulosConLecciones(id));
        model.addAttribute("resenas", lmsService.listarResenasDeCurso(id));
        model.addAttribute("desgloseEstrellas", lmsService.calcularDesgloseEstrellas(id));
        model.addAttribute("debates", lmsService.listarDebatesDeCurso(id));
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());

        boolean inscrito = false;
        boolean enWishlist = false;
        if (principal != null) {
            inscrito = lmsService.estaInscrito(principal.getId(), id);
            enWishlist = lmsService.estaEnWishlist(principal.getId(), id);
        }
        model.addAttribute("estaInscrito", inscrito);
        model.addAttribute("enWishlist", enWishlist);

        return "lms/detalle";
    }

    // 3. Comprar / Inscribirse en un Curso
    @PostMapping("/curso/{id}/comprar")
    public String comprarCurso(@PathVariable Long id,
                               @RequestParam(defaultValue = "Tarjeta de Crédito") String metodoPago,
                               @AuthenticationPrincipal UsuarioPrincipal principal,
                               RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        try {
            LmsCurso curso = lmsService.buscarCursoPorId(id).orElseThrow();
            lmsService.inscribirOComprarCurso(principal.getId(), id, curso.getPrecio(), metodoPago);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Felicitaciones! Te has inscrito en '" + curso.getTitulo() + "'. Accede al aula virtual.");
            return "redirect:/lms/aprender/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al procesar la inscripción: " + e.getMessage());
            return "redirect:/lms/curso/" + id;
        }
    }

    // 4. Aula Virtual de Aprendizaje (Student Learning Experience)
    @GetMapping("/aprender/{cursoId}")
    public String aulaVirtual(@PathVariable Long cursoId,
                              @RequestParam(required = false) Long leccionId,
                              @AuthenticationPrincipal UsuarioPrincipal principal,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        if (principal == null) {
            return "redirect:/login";
        }

        LmsCurso curso = lmsService.buscarCursoPorId(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        // Verificar inscripción (o permitir si es el instructor o admin)
        boolean esAdmin = principal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        boolean esInstructor = curso.getInstructor().getId().equals(principal.getId());
        boolean inscrito = lmsService.estaInscrito(principal.getId(), cursoId);

        if (!inscrito && !esAdmin && !esInstructor) {
            // Auto-inscribir al estudiante si entra al aula (para fluidez de prueba)
            lmsService.inscribirOComprarCurso(principal.getId(), cursoId, 0.0, "Acceso Académico");
        }

        Optional<LmsInscripcion> inscripcionOpt = lmsService.obtenerInscripcion(principal.getId(), cursoId);
        LmsInscripcion inscripcion = inscripcionOpt.orElse(null);

        List<LmsModulo> modulos = lmsService.listarModulosConLecciones(cursoId);
        List<LmsLeccion> todasLecciones = lmsService.listarTodasLeccionesDeCurso(cursoId);

        if (todasLecciones.isEmpty()) {
            redirectAttributes.addFlashAttribute("mensajeError", "El curso aún no tiene lecciones creadas.");
            return "redirect:/lms/curso/" + cursoId;
        }

        LmsLeccion leccionActual = null;
        if (leccionId != null) {
            leccionActual = todasLecciones.stream()
                    .filter(l -> l.getId().equals(leccionId))
                    .findFirst()
                    .orElse(todasLecciones.get(0));
        } else {
            leccionActual = todasLecciones.get(0);
        }

        // Obtener IDs de lecciones ya completadas por el estudiante
        Set<Long> leccionesCompletadas = (inscripcion != null)
                ? lmsService.obtenerIdsLeccionesCompletadas(inscripcion.getId())
                : Set.of();

        boolean bloqueadaPorGoteo = lmsService.esLeccionBloqueadaPorGoteo(leccionActual, inscripcion);
        long diasRestantesGoteo = lmsService.diasRestantesGoteo(leccionActual, inscripcion);

        Optional<LmsTareaEntrega> entregaOpt = (inscripcion != null)
                ? lmsService.obtenerEntrega(inscripcion.getId(), leccionActual.getId())
                : Optional.empty();

        model.addAttribute("curso", curso);
        model.addAttribute("modulos", modulos);
        model.addAttribute("todasLecciones", todasLecciones);
        model.addAttribute("leccionActual", leccionActual);
        model.addAttribute("inscripcion", inscripcion);
        model.addAttribute("completadasIds", leccionesCompletadas);
        model.addAttribute("bloqueadaPorGoteo", bloqueadaPorGoteo);
        model.addAttribute("diasRestantesGoteo", diasRestantesGoteo);
        model.addAttribute("entregaActual", entregaOpt.orElse(null));
        model.addAttribute("certificadoDisponible", inscripcion != null && inscripcion.getPorcentajeProgreso() != null && inscripcion.getPorcentajeProgreso() >= 100);
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());

        return "lms/aprender";
    }

    // 4b. Vista Previa Pública de Lección (Free Preview)
    @GetMapping("/curso/{cursoId}/preview/{leccionId}")
    public String vistaPreviaLeccion(@PathVariable Long cursoId,
                                     @PathVariable Long leccionId,
                                     Model model) {
        LmsCurso curso = lmsService.buscarCursoPorId(cursoId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));

        List<LmsModulo> modulos = lmsService.listarModulosConLecciones(cursoId);
        List<LmsLeccion> todasLecciones = lmsService.listarTodasLeccionesDeCurso(cursoId);

        LmsLeccion leccionActual = todasLecciones.stream()
                .filter(l -> l.getId().equals(leccionId))
                .findFirst()
                .orElse(todasLecciones.isEmpty() ? null : todasLecciones.get(0));

        model.addAttribute("curso", curso);
        model.addAttribute("modulos", modulos);
        model.addAttribute("todasLecciones", todasLecciones);
        model.addAttribute("leccionActual", leccionActual);
        model.addAttribute("esVistaPrevia", true);
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());

        return "lms/preview";
    }

    // 5. Marcar Lección como Completada
    @PostMapping("/aprender/{cursoId}/completar")
    public String marcarLeccionCompletada(@PathVariable Long cursoId,
                                          @RequestParam Long leccionId,
                                          @RequestParam(required = false) Long siguienteLeccionId,
                                          @AuthenticationPrincipal UsuarioPrincipal principal,
                                          RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";

        try {
            int progreso = lmsService.marcarLeccionCompletada(principal.getId(), cursoId, leccionId);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Lección completada! Tu progreso actual es del " + progreso + "%.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error: " + e.getMessage());
        }

        if (siguienteLeccionId != null) {
            return "redirect:/lms/aprender/" + cursoId + "?leccionId=" + siguienteLeccionId;
        }
        return "redirect:/lms/aprender/" + cursoId + "?leccionId=" + leccionId;
    }

    // 5b. Enviar Cuestionario / Evaluación (Quiz)
    @PostMapping("/aprender/{cursoId}/quiz")
    public String responderQuiz(@PathVariable Long cursoId,
                                @RequestParam Long leccionId,
                                @RequestParam String respuestaSeleccionada,
                                @RequestParam(required = false) Long siguienteLeccionId,
                                @AuthenticationPrincipal UsuarioPrincipal principal,
                                RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";

        try {
            boolean aprobado = lmsService.evaluarQuiz(leccionId, respuestaSeleccionada);
            if (aprobado) {
                int progreso = lmsService.marcarLeccionCompletada(principal.getId(), cursoId, leccionId);
                redirectAttributes.addFlashAttribute("mensajeExito", "¡Excelente! Has respondido correctamente el cuestionario. Progreso: " + progreso + "%.");
            } else {
                redirectAttributes.addFlashAttribute("mensajeError", "Respuesta incorrecta. Revisa el contenido y vuelve a intentarlo.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al procesar el cuestionario: " + e.getMessage());
        }

        return "redirect:/lms/aprender/" + cursoId + "?leccionId=" + leccionId;
    }

    // 5c. Entregar Tarea Práctica
    @PostMapping("/aprender/{cursoId}/tarea")
    public String entregarTarea(@PathVariable Long cursoId,
                                @RequestParam Long leccionId,
                                @RequestParam(required = false) String enlaceEntrega,
                                @RequestParam(required = false) String notasEstudiante,
                                @AuthenticationPrincipal UsuarioPrincipal principal,
                                RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";

        try {
            Optional<LmsInscripcion> inscripcionOpt = lmsService.obtenerInscripcion(principal.getId(), cursoId);
            if (inscripcionOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("mensajeError", "No tienes una inscripción activa en este curso.");
                return "redirect:/lms/curso/" + cursoId;
            }

            lmsService.entregarTarea(inscripcionOpt.get().getId(), leccionId, enlaceEntrega, notasEstudiante);
            int progreso = lmsService.marcarLeccionCompletada(principal.getId(), cursoId, leccionId);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Tarea entregada con éxito y evaluada! Progreso: " + progreso + "%.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al entregar tarea: " + e.getMessage());
        }

        return "redirect:/lms/aprender/" + cursoId + "?leccionId=" + leccionId;
    }

    // 5d. Certificado Oficial de Finalización
    @GetMapping("/curso/{cursoId}/certificado")
    public String verCertificado(@PathVariable Long cursoId,
                                 @AuthenticationPrincipal UsuarioPrincipal principal,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";

        Optional<LmsInscripcion> inscripcionOpt = lmsService.obtenerInscripcion(principal.getId(), cursoId);
        if (inscripcionOpt.isEmpty() || inscripcionOpt.get().getPorcentajeProgreso() < 100) {
            redirectAttributes.addFlashAttribute("mensajeError", "Debes completar el 100% de las lecciones del curso para desbloquear tu certificado oficial.");
            return "redirect:/lms/aprender/" + cursoId;
        }

        LmsCurso curso = lmsService.buscarCursoPorId(cursoId).orElseThrow();
        model.addAttribute("inscripcion", inscripcionOpt.get());
        model.addAttribute("curso", curso);
        model.addAttribute("estudiante", inscripcionOpt.get().getEstudiante());
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());
        return "lms/certificado";
    }

    // 6. Reseñas y Debates
    @PostMapping("/curso/{id}/resena")
    public String agregarResena(@PathVariable Long id,
                                @RequestParam Integer calificacion,
                                @RequestParam String comentario,
                                @AuthenticationPrincipal UsuarioPrincipal principal,
                                RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";
        try {
            lmsService.agregarResena(id, principal.getId(), calificacion, comentario);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Gracias por valorar este curso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms/curso/" + id;
    }

    @PostMapping("/curso/{id}/debate")
    public String crearDebate(@PathVariable Long id,
                              @RequestParam String titulo,
                              @RequestParam String pregunta,
                              @AuthenticationPrincipal UsuarioPrincipal principal,
                              RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";
        try {
            lmsService.crearDebate(id, principal.getId(), titulo, pregunta);
            redirectAttributes.addFlashAttribute("mensajeExito", "Pregunta publicada para el instructor.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms/curso/" + id;
    }

    @PostMapping("/curso/{id}/debate/{debateId}/responder")
    public String responderDebate(@PathVariable Long id,
                                  @PathVariable Long debateId,
                                  @RequestParam String respuesta,
                                  RedirectAttributes redirectAttributes) {
        try {
            lmsService.responderDebate(debateId, respuesta);
            redirectAttributes.addFlashAttribute("mensajeExito", "Respuesta enviada al estudiante.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms/curso/" + id;
    }

    // 7. Panel de Instructor / Creador de Cursos (Course Builder)
    @GetMapping("/instructor")
    public String panelInstructor(@AuthenticationPrincipal UsuarioPrincipal principal,
                                  @RequestParam(required = false) Long cursoGestionarId,
                                  Model model) {
        if (principal == null) return "redirect:/login";

        Long instructorId = principal.getId();
        // Si es admin, permitir ver como cualquier instructor o sus propios cursos
        model.addAttribute("stats", lmsService.obtenerEstadisticasInstructor(instructorId));
        List<LmsCurso> misCursos = lmsService.listarCursosPorInstructor(instructorId);
        model.addAttribute("misCursos", misCursos);
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());

        LmsCurso cursoGestionar = null;
        if (cursoGestionarId != null) {
            cursoGestionar = lmsService.buscarCursoPorId(cursoGestionarId).orElse(null);
        } else if (!misCursos.isEmpty()) {
            cursoGestionar = misCursos.get(0);
        }
        model.addAttribute("cursoGestionar", cursoGestionar);

        if (cursoGestionar != null) {
            model.addAttribute("modulos", lmsService.listarModulosConLecciones(cursoGestionar.getId()));
        }

        return "lms/instructor";
    }

    @PostMapping("/instructor/crear-curso")
    public String crearCurso(@ModelAttribute LmsCurso curso,
                             @AuthenticationPrincipal UsuarioPrincipal principal,
                             RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";

        try {
            Usuario instructor = usuarioService.buscarPorId(principal.getId()).orElseThrow();
            curso.setInstructor(instructor);
            LmsCurso guardado = lmsService.guardarCurso(curso);

            // Crear un módulo introductorio por defecto
            lmsService.agregarModulo(guardado.getId(), "Módulo 1: Introducción y Primeros Pasos", 1);

            redirectAttributes.addFlashAttribute("mensajeExito", "Curso '" + curso.getTitulo() + "' creado con éxito. Ahora agrega lecciones a tus módulos.");
            return "redirect:/lms/instructor?cursoGestionarId=" + guardado.getId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al crear curso: " + e.getMessage());
            return "redirect:/lms/instructor";
        }
    }

    @PostMapping("/instructor/modulo/crear")
    public String agregarModulo(@RequestParam Long cursoId,
                                @RequestParam String titulo,
                                RedirectAttributes redirectAttributes) {
        try {
            lmsService.agregarModulo(cursoId, titulo, null);
            redirectAttributes.addFlashAttribute("mensajeExito", "Módulo agregado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms/instructor?cursoGestionarId=" + cursoId;
    }

    @PostMapping("/instructor/leccion/crear")
    public String agregarLeccion(@RequestParam Long cursoId,
                                 @RequestParam Long moduloId,
                                 @RequestParam String titulo,
                                 @RequestParam TipoLeccion tipo,
                                 @RequestParam(required = false) String contenido,
                                 @RequestParam(required = false) String videoUrl,
                                 @RequestParam(defaultValue = "15") Integer duracionMinutos,
                                 @RequestParam(required = false) String archivoAdjuntoNombre,
                                 @RequestParam(required = false) String archivoAdjuntoUrl,
                                 @RequestParam(defaultValue = "false") boolean vistaPrevia,
                                 @RequestParam(defaultValue = "0") Integer diasGoteo,
                                 @RequestParam(required = false) String quizPregunta,
                                 @RequestParam(required = false) String quizOpcionA,
                                 @RequestParam(required = false) String quizOpcionB,
                                 @RequestParam(required = false) String quizOpcionC,
                                 @RequestParam(defaultValue = "A") String quizRespuestaCorrecta,
                                 @RequestParam(required = false) String quizExplicacion,
                                 @RequestParam(required = false) String tareaInstrucciones,
                                 RedirectAttributes redirectAttributes) {
        try {
            lmsService.agregarLeccionCompleta(moduloId, titulo, tipo, contenido, videoUrl, duracionMinutos,
                    archivoAdjuntoNombre, archivoAdjuntoUrl, vistaPrevia, diasGoteo,
                    quizPregunta, quizOpcionA, quizOpcionB, quizOpcionC, quizRespuestaCorrecta, quizExplicacion,
                    tareaInstrucciones);
            redirectAttributes.addFlashAttribute("mensajeExito", "Lección agregada correctamente al temario con todas sus configuraciones avanzadas.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms/instructor?cursoGestionarId=" + cursoId;
    }

    // 8. Configuración de Marca y Academia (White-Label)
    @GetMapping("/configuracion")
    public String configuracionAcademia(Model model) {
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());
        model.addAttribute("statsGlobales", lmsService.obtenerEstadisticasGlobales());
        return "lms/configuracion";
    }

    @PostMapping("/configuracion/guardar")
    public String guardarConfiguracion(@ModelAttribute LmsAcademiaConfig config, RedirectAttributes redirectAttributes) {
        try {
            lmsService.actualizarConfiguracion(config);
            redirectAttributes.addFlashAttribute("mensajeExito", "Configuración de marca y academia actualizada con éxito.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms/configuracion";
    }

    // 9. Wishlist / Lista de Deseos
    @PostMapping("/curso/{id}/wishlist")
    public String toggleWishlist(@PathVariable Long id,
                                 @AuthenticationPrincipal UsuarioPrincipal principal,
                                 RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";
        try {
            boolean agregado = lmsService.toggleWishlist(principal.getId(), id);
            if (agregado) {
                redirectAttributes.addFlashAttribute("mensajeExito", "Curso agregado a tu Lista de Deseos ❤️");
            } else {
                redirectAttributes.addFlashAttribute("mensajeExito", "Curso eliminado de tu Lista de Deseos.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms/curso/" + id;
    }

    @GetMapping("/wishlist")
    public String verWishlist(@AuthenticationPrincipal UsuarioPrincipal principal, Model model) {
        if (principal == null) return "redirect:/login";
        model.addAttribute("wishlist", lmsService.listarWishlistDeUsuario(principal.getId()));
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());
        return "lms/wishlist";
    }

    // 10. Sistema de Retiro del Instructor
    @PostMapping("/instructor/solicitar-retiro")
    public String solicitarRetiro(@RequestParam Double monto,
                                  @RequestParam String metodoPago,
                                  @RequestParam String detallesCuenta,
                                  @AuthenticationPrincipal UsuarioPrincipal principal,
                                  RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";
        try {
            lmsService.solicitarRetiro(principal.getId(), monto, metodoPago, detallesCuenta);
            redirectAttributes.addFlashAttribute("mensajeExito", "Solicitud de retiro enviada correctamente. El administrador la procesará a la brevedad.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al procesar el retiro: " + e.getMessage());
        }
        return "redirect:/lms/instructor";
    }

    @PostMapping("/admin/retiro/{id}/procesar")
    public String procesarRetiroAdmin(@PathVariable Long id,
                                      @RequestParam String nuevoEstado,
                                      RedirectAttributes redirectAttributes) {
        try {
            lmsService.procesarRetiro(id, nuevoEstado);
            redirectAttributes.addFlashAttribute("mensajeExito", "Estado de la solicitud de retiro actualizado a " + nuevoEstado + ".");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms/instructor";
    }

    // 11. Gestor de Medios (Media Manager)
    @GetMapping("/medios")
    public String gestorMedios(Model model) {
        model.addAttribute("medios", lmsService.listarArchivosMedia());
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());
        return "lms/medios";
    }

    @PostMapping("/medios/subir")
    public String registrarMedio(@RequestParam String nombre,
                                 @RequestParam String tipo,
                                 @RequestParam String url,
                                 @RequestParam(defaultValue = "150") Long tamanoKb,
                                 @AuthenticationPrincipal UsuarioPrincipal principal,
                                 RedirectAttributes redirectAttributes) {
        try {
            lmsService.registrarArchivoMedia(nombre, tipo, url, tamanoKb, principal != null ? principal.getId() : null);
            redirectAttributes.addFlashAttribute("mensajeExito", "Archivo registrado exitosamente en el Gestor de Medios.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms/medios";
    }
}
