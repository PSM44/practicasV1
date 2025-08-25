package com.ipss.practicas.web.auth;

import com.ipss.practicas.model.enums.RolUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
            throws Exception {

        String uri = req.getRequestURI();

        // Rutas públicas
        if (uri.startsWith("/login") || uri.startsWith("/logout")
                || uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/img/")) {
            return true;
        }

        HttpSession session = req.getSession(false);
        SessionUser su = (session != null) ? (SessionUser) session.getAttribute("SESSION_USER") : null;
        if (su == null) {
            res.sendRedirect("/login");
            return false;
        }

        // Reglas por rol
        if (uri.startsWith("/estudiante") && su.rol() != RolUsuario.ESTUDIANTE) {
            res.sendRedirect("/");
            return false;
        }
        if (uri.startsWith("/profesor") && su.rol() != RolUsuario.PROFESOR) {
            res.sendRedirect("/");
            return false;
        }
        return true;
    }
}
