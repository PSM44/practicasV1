package com.ipss.practicas.repository;

import com.ipss.practicas.model.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByNombreIgnoreCase(String nombre);
    boolean existsByNombreIgnoreCase(String nombre);
}
