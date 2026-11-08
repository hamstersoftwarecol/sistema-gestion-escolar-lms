package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Authentication authentication,
                        @RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("loginError", "Credenciales incorrectas. Verifica tu correo y contraseña.");
        }
        if (logout != null) {
            model.addAttribute("logoutSuccess", "Has cerrado sesión correctamente.");
        }
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
