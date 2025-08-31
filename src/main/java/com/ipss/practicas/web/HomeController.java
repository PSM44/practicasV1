package com.ipss.practicas.web;

import com.ipss.practicas.model.enums.RolUsuario;
import com.ipss.practicas.web.auth.SessionUser;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        SessionUser su = (SessionUser) (session != null ? session.getAttribute("SESSION_USER") : null);
        if (su == null) return "redirect:/login";
        return (su.rol() == RolUsuario.ESTUDIANTE) ? "home-estudiante" : "home-profesor";
    }
}
