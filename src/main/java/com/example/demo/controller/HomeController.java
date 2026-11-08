package com.example.demo.controller;

import com.example.demo.model.RolUsuario;
import com.example.demo.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;
    private final CursoService cursoService;
    private final MatriculaService matriculaService;
    private final FinanzasService finanzasService;

    public HomeController(UsuarioService usuarioService,
                          CursoService cursoService,
                          MatriculaService matriculaService,
                          FinanzasService finanzasService) {
        this.usuarioService = usuarioService;
        this.cursoService = cursoService;
        this.matriculaService = matriculaService;
        this.finanzasService = finanzasService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("totalEstudiantes", usuarioService.contarPorRol(RolUsuario.ESTUDIANTE));
        model.addAttribute("totalProfesores", usuarioService.contarPorRol(RolUsuario.PROFESOR));
        model.addAttribute("totalCursos", cursoService.listarTodos().size());
        model.addAttribute("totalMatriculas", matriculaService.listarTodas().size());
        model.addAttribute("statsFinanzas", finanzasService.obtenerEstadisticas());

        model.addAttribute("profesores", usuarioService.listarPorRol(RolUsuario.PROFESOR));
        model.addAttribute("estudiantes", usuarioService.listarPorRol(RolUsuario.ESTUDIANTE));

        return "index";
    }
}
