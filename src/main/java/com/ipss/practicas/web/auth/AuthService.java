package com.ipss.practicas.web.auth;

import com.ipss.practicas.model.entity.Usuario;
import com.ipss.practicas.repository.UsuarioRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public AuthService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario authenticate(String username, String rawPassword) {
        return usuarioRepository.findByUsername(username)
                .filter(u -> Boolean.TRUE.equals(u.getActivo()))
                .filter(u -> passwordMatches(rawPassword, u.getPasswordHash()))
                .orElse(null);
    }

    private boolean passwordMatches(String raw, String stored) {
        if (stored == null) return false;
        // Si está hasheado (BCrypt):
        if (stored.startsWith("$2a$") || stored.startsWith("$2b$") || stored.startsWith("$2y$")) {
            return encoder.matches(raw, stored);
        }
        // Fallback: texto plano (útil para las semillas “1234”)
        return stored.equals(raw);
    }

    /** Útil para migrar a hash más adelante */
    public String hash(String raw) { return encoder.encode(raw); }
}
