package com.example.demo.controller;

import com.example.demo.model.Calificacion;
import com.example.demo.model.Curso;
import com.example.demo.model.RolUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.security.UsuarioPrincipal;
import com.example.demo.service.CalificacionService;
import com.example.demo.service.CursoService;
import com.example.demo.service.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/profesor")
public class ProfesorController {

    private final UsuarioService usuarioService;
    private final CursoService cursoService;
    private final CalificacionService calificacionService;

    public ProfesorController(UsuarioService usuarioService,
                              CursoService cursoService,
                              CalificacionService calificacionService) {
        this.usuarioService = usuarioService;
        this.cursoService = cursoService;
        this.calificacionService = calificacionService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal UsuarioPrincipal principal,
                        @RequestParam(required = false) Long profesorId,
                        @RequestParam(required = false) Long cursoId,
                        Model model) {
        List<Usuario> profesores = usuarioService.listarPorRol(RolUsuario.PROFESOR);
        model.addAttribute("profesores", profesores);

        if (profesores.isEmpty()) {
            return "profesor/index";
        }

        Usuario profesorActual = null;
        if (principal != null && principal.getUsuario().getRol() == RolUsuario.PROFESOR) {
            profesorActual = principal.getUsuario();
        } else if (profesorId != null) {
            profesorActual = usuarioService.buscarPorId(profesorId).orElse(profesores.get(0));
        } else {
            profesorActual = profesores.get(0);
        }
        model.addAttribute("profesorActual", profesorActual);

        List<Curso> misCursos = cursoService.listarPorProfesor(profesorActual.getId());
        model.addAttribute("misCursos", misCursos);

        Curso cursoSeleccionado = null;
        if (!misCursos.isEmpty()) {
            if (cursoId != null) {
                cursoSeleccionado = misCursos.stream()
                        .filter(c -> c.getId().equals(cursoId))
                        .findFirst()
                        .orElse(misCursos.get(0));
            } else {
                cursoSeleccionado = misCursos.get(0);
            }
        }
        model.addAttribute("cursoSeleccionado", cursoSeleccionado);

        if (cursoSeleccionado != null) {
            List<Calificacion> calificaciones = calificacionService.listarPorCurso(cursoSeleccionado.getId());
            model.addAttribute("calificaciones", calificaciones);
        }

        return "profesor/index";
    }

    @PostMapping("/calificar")
    public String calificar(@RequestParam Long calificacionId,
                            @RequestParam Double nota1,
                            @RequestParam Double nota2,
                            @RequestParam Double notaFinal,
                            @RequestParam(required = false) String observaciones,
                            @RequestParam(required = false) Long profesorId,
                            @RequestParam(required = false) Long cursoId,
                            RedirectAttributes redirectAttributes) {
        try {
            calificacionService.actualizarNotas(calificacionId, nota1, nota2, notaFinal, observaciones);
            redirectAttributes.addFlashAttribute("mensajeExito", "Calificaciones actualizadas exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al guardar notas: " + e.getMessage());
        }
        return "redirect:/profesor?profesorId=" + (profesorId != null ? profesorId : "") + "&cursoId=" + (cursoId != null ? cursoId : "");
    }
}
