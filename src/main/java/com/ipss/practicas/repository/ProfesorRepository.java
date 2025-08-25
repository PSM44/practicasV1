package com.ipss.practicas.repository;

import com.ipss.practicas.model.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Optional<Profesor> findByUsuarioId(Long usuarioId);
    Optional<Profesor> findByEmail(String email);
}
