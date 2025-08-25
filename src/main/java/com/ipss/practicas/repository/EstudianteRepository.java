package com.ipss.practicas.repository;

import com.ipss.practicas.model.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Optional<Estudiante> findByUsuarioId(Long usuarioId);
    Optional<Estudiante> findByEmail(String email);
}
