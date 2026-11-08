package com.example.demo.controller;

import com.example.demo.model.EstadoMatricula;
import com.example.demo.model.RolUsuario;
import com.example.demo.service.CursoService;
import com.example.demo.service.MatriculaService;
import com.example.demo.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final UsuarioService usuarioService;
    private final CursoService cursoService;

    public MatriculaController(MatriculaService matriculaService,
                               UsuarioService usuarioService,
                               CursoService cursoService) {
        this.matriculaService = matriculaService;
        this.usuarioService = usuarioService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("matriculas", matriculaService.listarTodas());
        model.addAttribute("estudiantes", usuarioService.listarPorRol(RolUsuario.ESTUDIANTE));
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("estados", EstadoMatricula.values());
        return "matriculas/index";
    }

    @PostMapping("/crear")
    public String matricular(@RequestParam Long estudianteId,
                             @RequestParam Long cursoId,
                             RedirectAttributes redirectAttributes) {
        try {
            matriculaService.matricular(estudianteId, cursoId);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Matrícula registrada exitosamente! Se generó la orden de cobro automáticamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/matriculas";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id,
                                @RequestParam EstadoMatricula estado,
                                RedirectAttributes redirectAttributes) {
        try {
            matriculaService.cambiarEstado(id, estado);
            redirectAttributes.addFlashAttribute("mensajeExito", "Estado de matrícula actualizado a " + estado.getEtiqueta());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar estado: " + e.getMessage());
        }
        return "redirect:/matriculas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            matriculaService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Matrícula eliminada correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al eliminar matrícula: " + e.getMessage());
        }
        return "redirect:/matriculas";
    }
}
