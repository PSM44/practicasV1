package com.ipss.practicas.service;

import com.ipss.practicas.model.entity.Empresa;
import com.ipss.practicas.repository.EmpresaRepository;
import com.ipss.practicas.service.exception.BusinessException;
import com.ipss.practicas.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository repo;

    public EmpresaService(EmpresaRepository repo) {
        this.repo = repo;
    }

    @Transactional(readOnly = true)
    public List<Empresa> listar() {
        return repo.findAll();
    }

    @Transactional
    public Empresa crear(Empresa e) {
        if (e.getNombre() == null || e.getNombre().isBlank()) {
            throw new BusinessException("El nombre de la empresa es obligatorio.");
        }
        if (repo.existsByNombreIgnoreCase(e.getNombre())) {
            throw new BusinessException("Ya existe una empresa con ese nombre.");
        }
        return repo.save(e);
    }

    @Transactional
    public Empresa actualizar(Long id, Empresa datos) {
        Empresa e = repo.findById(id).orElseThrow(() ->
                new NotFoundException("Empresa no encontrada"));
        if (datos.getNombre() != null && !datos.getNombre().isBlank()) {
            e.setNombre(datos.getNombre());
        }
        e.setRut(datos.getRut());
        e.setDireccion(datos.getDireccion());
        e.setTelefono(datos.getTelefono());
        e.setEmail(datos.getEmail());
        return repo.save(e);
    }
}
