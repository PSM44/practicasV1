package com.ipss.practicas.repository;

import com.ipss.practicas.model.entity.JefeDirecto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JefeDirectoRepository extends JpaRepository<JefeDirecto, Long> {
    List<JefeDirecto> findByEmpresaId(Long empresaId);
    Optional<JefeDirecto> findByEmpresaIdAndNombreIgnoreCase(Long empresaId, String nombre);
}
