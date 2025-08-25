package com.ipss.practicas.repository;

import com.ipss.practicas.model.entity.Practica;
import com.ipss.practicas.model.enums.EstadoPractica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PracticaRepository extends JpaRepository<Practica, Long> {

    Optional<Practica> findByIdAndActivoTrue(Long id);

    Page<Practica> findByEstudianteIdAndActivoTrue(Long estudianteId, Pageable pageable);

    Page<Practica> findByEmpresaIdAndActivoTrue(Long empresaId, Pageable pageable);

    Page<Practica> findByProfesorSupervisorIdAndActivoTrue(Long profesorId, Pageable pageable);

    List<Practica> findByEstadoAndActivoTrue(EstadoPractica estado);

    List<Practica> findByFechaInicioBetweenAndActivoTrue(LocalDate desde, LocalDate hasta);
}
