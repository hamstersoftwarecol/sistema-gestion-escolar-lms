package com.example.demo.controller;

import com.example.demo.lms.model.LmsPaginaDinamica;
import com.example.demo.lms.service.LmsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/paginas")
public class PaginaDinamicaController {

    private final LmsService lmsService;

    public PaginaDinamicaController(LmsService lmsService) {
        this.lmsService = lmsService;
    }

    @GetMapping("/{slug}")
    public String verPagina(@PathVariable String slug, Model model) {
        LmsPaginaDinamica pagina = lmsService.buscarPaginaPorSlug(slug)
                .orElse(new LmsPaginaDinamica(slug, "Página Informativa", "Contenido en preparación para la academia.", "fa-file-lines"));

        model.addAttribute("pagina", pagina);
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());
        return "paginas/detalle";
    }
}
