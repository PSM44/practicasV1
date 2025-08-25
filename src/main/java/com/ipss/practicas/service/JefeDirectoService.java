package com.ipss.practicas.service;

import com.ipss.practicas.model.entity.Empresa;
import com.ipss.practicas.model.entity.JefeDirecto;
import com.ipss.practicas.repository.EmpresaRepository;
import com.ipss.practicas.repository.JefeDirectoRepository;
import com.ipss.practicas.service.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JefeDirectoService {

    private final JefeDirectoRepository repo;
    private final EmpresaRepository empresaRepo;

    public JefeDirectoService(JefeDirectoRepository repo, EmpresaRepository empresaRepo) {
        this.repo = repo;
        this.empresaRepo = empresaRepo;
    }

    @Transactional(readOnly = true)
    public List<JefeDirecto> listarPorEmpresa(Long empresaId) {
        return repo.findByEmpresaId(empresaId);
    }

    @Transactional
    public JefeDirecto crear(Long empresaId, JefeDirecto datos) {
        Empresa emp = empresaRepo.findById(empresaId)
                .orElseThrow(() -> new NotFoundException("Empresa no encontrada"));
        datos.setEmpresa(emp);
        return repo.save(datos);
    }
}
