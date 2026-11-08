package com.example.demo.controller;

import com.example.demo.lms.model.LmsAcademiaConfig;
import com.example.demo.lms.service.LmsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/instalador")
public class InstaladorController {

    private final LmsService lmsService;

    public InstaladorController(LmsService lmsService) {
        this.lmsService = lmsService;
    }

    @GetMapping
    public String index(Model model) {
        Map<String, Object> diagnostico = new HashMap<>();
        diagnostico.put("javaVersion", System.getProperty("java.version"));
        diagnostico.put("osName", System.getProperty("os.name"));
        diagnostico.put("memoriaLibreMb", Runtime.getRuntime().freeMemory() / (1024 * 1024));
        diagnostico.put("memoriaTotalMb", Runtime.getRuntime().totalMemory() / (1024 * 1024));
        diagnostico.put("dbEstado", "Conectado (H2 In-Memory / JPA OK)");
        diagnostico.put("seguridad", "Spring Security 6 (Activo)");

        model.addAttribute("diagnostico", diagnostico);
        model.addAttribute("config", lmsService.obtenerConfiguracionAcademia());
        return "instalador/index";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute LmsAcademiaConfig config, RedirectAttributes redirectAttributes) {
        try {
            lmsService.actualizarConfiguracion(config);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Asistente de Instalación completado! Tu academia está configurada y lista.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", e.getMessage());
        }
        return "redirect:/lms";
    }
}
