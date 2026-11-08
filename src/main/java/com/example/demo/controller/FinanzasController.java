package com.example.demo.controller;

import com.example.demo.model.RolUsuario;
import com.example.demo.service.FinanzasService;
import com.example.demo.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/finanzas")
public class FinanzasController {

    private final FinanzasService finanzasService;
    private final UsuarioService usuarioService;

    public FinanzasController(FinanzasService finanzasService, UsuarioService usuarioService) {
        this.finanzasService = finanzasService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("transacciones", finanzasService.listarTodas());
        model.addAttribute("stats", finanzasService.obtenerEstadisticas());
        model.addAttribute("estudiantes", usuarioService.listarPorRol(RolUsuario.ESTUDIANTE));
        return "finanzas/index";
    }

    @PostMapping("/pagar/{id}")
    public String registrarPago(@PathVariable Long id,
                                @RequestParam(defaultValue = "En Línea / PSE") String metodoPago,
                                RedirectAttributes redirectAttributes) {
        try {
            finanzasService.registrarPago(id, metodoPago);
            redirectAttributes.addFlashAttribute("mensajeExito", "¡Pago registrado exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al procesar pago: " + e.getMessage());
        }
        return "redirect:/finanzas";
    }

    @PostMapping("/crear")
    public String crearCobro(@RequestParam Long estudianteId,
                             @RequestParam String concepto,
                             @RequestParam Double monto,
                             @RequestParam(required = false) String fechaVencimiento,
                             RedirectAttributes redirectAttributes) {
        try {
            LocalDate fVenc = (fechaVencimiento != null && !fechaVencimiento.isBlank())
                    ? LocalDate.parse(fechaVencimiento)
                    : LocalDate.now().plusDays(30);

            finanzasService.crearCobro(estudianteId, concepto, monto, fVenc);
            redirectAttributes.addFlashAttribute("mensajeExito", "Cobro generado exitosamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al generar cobro: " + e.getMessage());
        }
        return "redirect:/finanzas";
    }
}
