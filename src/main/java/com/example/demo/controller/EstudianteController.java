package com.example.demo.controller;

import com.example.demo.model.Calificacion;
import com.example.demo.model.RolUsuario;
import com.example.demo.model.TransaccionFinanciera;
import com.example.demo.model.Usuario;
import com.example.demo.security.UsuarioPrincipal;
import com.example.demo.service.CalificacionService;
import com.example.demo.service.FinanzasService;
import com.example.demo.service.MatriculaService;
import com.example.demo.service.UsuarioService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    private final UsuarioService usuarioService;
    private final MatriculaService matriculaService;
    private final CalificacionService calificacionService;
    private final FinanzasService finanzasService;

    public EstudianteController(UsuarioService usuarioService,
                                MatriculaService matriculaService,
                                CalificacionService calificacionService,
                                FinanzasService finanzasService) {
        this.usuarioService = usuarioService;
        this.matriculaService = matriculaService;
        this.calificacionService = calificacionService;
        this.finanzasService = finanzasService;
    }

    @GetMapping
    public String index(@AuthenticationPrincipal UsuarioPrincipal principal,
                        @RequestParam(required = false) Long estudianteId,
                        Model model) {
        List<Usuario> estudiantes = usuarioService.listarPorRol(RolUsuario.ESTUDIANTE);
        model.addAttribute("estudiantes", estudiantes);

        if (estudiantes.isEmpty()) {
            return "estudiante/index";
        }

        Usuario estudianteActual = null;
        if (principal != null && principal.getUsuario().getRol() == RolUsuario.ESTUDIANTE) {
            estudianteActual = principal.getUsuario();
        } else if (estudianteId != null) {
            estudianteActual = usuarioService.buscarPorId(estudianteId).orElse(estudiantes.get(0));
        } else {
            estudianteActual = estudiantes.get(0);
        }
        model.addAttribute("estudianteActual", estudianteActual);

        // Materias y Calificaciones
        List<Calificacion> calificaciones = calificacionService.listarPorEstudiante(estudianteActual.getId());
        model.addAttribute("calificaciones", calificaciones);

        // Promedio acumulado
        double promedioGeneral = calificaciones.stream()
                .mapToDouble(Calificacion::getPromedio)
                .average()
                .orElse(0.0);
        model.addAttribute("promedioGeneral", Math.round(promedioGeneral * 100.0) / 100.0);

        // Finanzas del estudiante
        List<TransaccionFinanciera> finanzas = finanzasService.listarPorEstudiante(estudianteActual.getId());
        model.addAttribute("transacciones", finanzas);

        double deudaPendiente = finanzas.stream()
                .filter(t -> t.getEstado() != com.example.demo.model.EstadoFinanciero.PAGADO)
                .mapToDouble(TransaccionFinanciera::getMonto)
                .sum();
        model.addAttribute("deudaPendiente", deudaPendiente);

        return "estudiante/index";
    }

    @PostMapping("/pagar/{id}")
    public String pagar(@PathVariable Long id,
                        @RequestParam Long estudianteId,
                        RedirectAttributes redirectAttributes) {
        try {
            finanzasService.registrarPago(id, "Pago en Línea Portal Estudiante");
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Pago procesado con éxito! Tu estado de cuenta ha sido actualizado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al procesar el pago: " + e.getMessage());
        }
        return "redirect:/estudiante?estudianteId=" + estudianteId;
    }
}
