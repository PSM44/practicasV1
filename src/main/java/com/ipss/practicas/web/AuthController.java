package com.ipss.practicas.web;

import com.ipss.practicas.model.entity.Usuario;
import com.ipss.practicas.web.auth.AuthService;
import com.ipss.practicas.web.auth.LoginForm;
import com.ipss.practicas.web.auth.SessionUser;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) { this.authService = authService; }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("form", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String doLogin(@Valid @ModelAttribute("form") LoginForm form,
                          BindingResult br, HttpSession session, Model model) {
        if (br.hasErrors()) return "login";

        Usuario u = authService.authenticate(form.getUsername(), form.getPassword());
        if (u == null) {
            br.reject("bad.credentials", "Usuario o contraseña inválidos");
            return "login";
        }
        session.setAttribute("SESSION_USER", new SessionUser(u.getId(), u.getRol(), u.getUsername()));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        if (session != null) session.invalidate();
        return "redirect:/login";
    }
}
