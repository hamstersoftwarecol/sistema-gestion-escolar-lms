package com.example.demo.controller;

import com.example.demo.lms.model.LmsArticuloBlog;
import com.example.demo.lms.service.LmsService;
import com.example.demo.security.UsuarioPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/blog")
public class BlogController {

    private final LmsService lmsService;

    public BlogController(LmsService lmsService) {
        this.lmsService = lmsService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("articulos", lmsService.listarArticulosBlog());
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());
        return "blog/index";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        LmsArticuloBlog articulo = lmsService.buscarArticuloPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado"));
        model.addAttribute("articulo", articulo);
        model.addAttribute("otrosArticulos", lmsService.listarArticulosBlog());
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());
        return "blog/detalle";
    }

    @PostMapping("/crear")
    public String crear(@RequestParam String titulo,
                        @RequestParam String resumen,
                        @RequestParam String contenido,
                        @RequestParam(required = false) String imagenUrl,
                        @RequestParam(defaultValue = "Tecnología & Educación") String categoria,
                        @RequestParam(defaultValue = "5") Integer leidoMinutos,
                        @AuthenticationPrincipal UsuarioPrincipal principal,
                        RedirectAttributes redirectAttributes) {
        if (principal == null) return "redirect:/login";

        try {
            String img = imagenUrl != null && !imagenUrl.isBlank()
                    ? imagenUrl
                    : "https://images.unsplash.com/photo-1516321318423-f06f85e504b3?w=800&auto=format&fit=crop&q=80";
            lmsService.crearArticuloBlog(titulo, resumen, contenido, img, principal.getId(), categoria, leidoMinutos);
            redirectAttributes.addFlashAttribute("mensajeExito", "Artículo publicado exitosamente en el Blog de la academia.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al publicar: " + e.getMessage());
        }

        return "redirect:/blog";
    }
}
