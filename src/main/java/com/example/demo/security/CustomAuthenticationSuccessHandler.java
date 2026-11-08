package com.example.demo.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            String rol = authority.getAuthority();
            if ("ROLE_ADMIN".equals(rol)) {
                response.sendRedirect("/admin");
                return;
            } else if ("ROLE_PROFESOR".equals(rol)) {
                response.sendRedirect("/profesor");
                return;
            } else if ("ROLE_ESTUDIANTE".equals(rol)) {
                response.sendRedirect("/estudiante");
                return;
            }
        }
        response.sendRedirect("/");
    }
}
