package com.example.demo.controller;

import com.example.demo.model.Curso;
import com.example.demo.model.RolUsuario;
import com.example.demo.model.Usuario;
import com.example.demo.service.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UsuarioService usuarioService;
    private final CursoService cursoService;
    private final MatriculaService matriculaService;
    private final FinanzasService finanzasService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UsuarioService usuarioService,
                           CursoService cursoService,
                           MatriculaService matriculaService,
                           FinanzasService finanzasService,
                           PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.cursoService = cursoService;
        this.matriculaService = matriculaService;
        this.finanzasService = finanzasService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("totalEstudiantes", usuarioService.contarPorRol(RolUsuario.ESTUDIANTE));
        model.addAttribute("totalProfesores", usuarioService.contarPorRol(RolUsuario.PROFESOR));
        model.addAttribute("cursos", cursoService.listarTodos());
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("profesores", usuarioService.listarPorRol(RolUsuario.PROFESOR));
        model.addAttribute("matriculas", matriculaService.listarTodas());
        model.addAttribute("statsFinanzas", finanzasService.obtenerEstadisticas());

        model.addAttribute("nuevoCurso", new Curso());
        model.addAttribute("nuevoUsuario", new Usuario());

        return "admin/dashboard";
    }

    @PostMapping("/cursos/crear")
    public String crearCurso(@ModelAttribute Curso curso,
                             @RequestParam(required = false) Long profesorId,
                             RedirectAttributes redirectAttributes) {
        try {
            if (profesorId != null) {
                usuarioService.buscarPorId(profesorId).ifPresent(curso::setProfesor);
            }
            cursoService.guardar(curso);
            redirectAttributes.addFlashAttribute("mensajeExito", "Curso '" + curso.getNombre() + "' creado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al crear curso: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/usuarios/crear")
    public String crearUsuario(@ModelAttribute Usuario usuario,
                               @RequestParam(required = false) String rawPassword,
                               RedirectAttributes redirectAttributes) {
        try {
            String pass = (rawPassword != null && !rawPassword.isBlank()) ? rawPassword : "password123";
            usuario.setPassword(passwordEncoder.encode(pass));
            usuarioService.guardar(usuario);
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario '" + usuario.getNombre() + "' registrado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al crear usuario: " + e.getMessage());
        }
        return "redirect:/admin";
    }

    @PostMapping("/usuarios/{id}/eliminar")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            usuarioService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Usuario eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "No se pudo eliminar el usuario (puede tener cursos o matrículas vinculadas).");
        }
        return "redirect:/admin";
    }
}
