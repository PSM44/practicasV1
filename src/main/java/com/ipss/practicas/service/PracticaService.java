package com.ipss.practicas.service;

import com.ipss.practicas.model.entity.*;
import com.ipss.practicas.model.enums.EstadoPractica;
import com.ipss.practicas.service.exception.BusinessException;
import com.ipss.practicas.service.exception.NotFoundException;
import com.ipss.practicas.repository.*;  // ← ¡con punto y coma!
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class PracticaService {

    private final PracticaRepository practicaRepo;
    private final EstudianteRepository estudianteRepo;
    private final EmpresaRepository empresaRepo;
    private final JefeDirectoRepository jefeRepo;
    private final ProfesorRepository profesorRepo;

    public PracticaService(PracticaRepository practicaRepo,
                           EstudianteRepository estudianteRepo,
                           EmpresaRepository empresaRepo,
                           JefeDirectoRepository jefeRepo,
                           ProfesorRepository profesorRepo) {
        this.practicaRepo = practicaRepo;
        this.estudianteRepo = estudianteRepo;
        this.empresaRepo = empresaRepo;
        this.jefeRepo = jefeRepo;
        this.profesorRepo = profesorRepo;
    }

    // ---------- VALIDACIONES DE NEGOCIO ----------
    private void validarFechas(LocalDate inicio, LocalDate termino) {
        if (inicio == null || termino == null) {
            throw new BusinessException("Las fechas de inicio y término son obligatorias.");
        }
        if (termino.isBefore(inicio)) {
            throw new BusinessException("La fecha de término no puede ser anterior a la de inicio.");
        }
    }

    private void validarHoras(Integer horas) {
        if (horas == null || horas < 1) {
            throw new BusinessException("Las horas totales deben ser al menos 1.");
        }
    }

    // ---------- CRUD / QUERIES ----------
    @Transactional
    public Practica crear(Long estudianteId,
                          Long empresaId,
                          Long jefeDirectoId,
                          Long profesorId,
                          LocalDate fechaInicio,
                          LocalDate fechaTermino,
                          Integer horasTotales,
                          String descripcion,
                          EstadoPractica estado) {

        Estudiante est = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new NotFoundException("Estudiante no encontrado"));
        Empresa emp = empresaRepo.findById(empresaId)
                .orElseThrow(() -> new NotFoundException("Empresa no encontrada"));
        JefeDirecto jefe = jefeRepo.findById(jefeDirectoId)
                .orElseThrow(() -> new NotFoundException("Jefe directo no encontrado"));
        Profesor prof = profesorRepo.findById(profesorId)
                .orElseThrow(() -> new NotFoundException("Profesor supervisor no encontrado"));

        validarFechas(fechaInicio, fechaTermino);
        validarHoras(horasTotales);

        Practica p = new Practica(est, emp, jefe, prof, fechaInicio, fechaTermino,
                                  horasTotales, descripcion, estado);
        return practicaRepo.save(p);
    }

    @Transactional(readOnly = true)
    public Practica obtenerActiva(Long id) {
        return practicaRepo.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new NotFoundException("Práctica no encontrada o eliminada."));
    }

    @Transactional
    public Practica actualizar(Long id,
                               Long empresaId,
                               Long jefeDirectoId,
                               Long profesorId,
                               LocalDate fechaInicio,
                               LocalDate fechaTermino,
                               Integer horasTotales,
                               String descripcion,
                               EstadoPractica estado) {

        Practica p = obtenerActiva(id);

        if (empresaId != null) {
            Empresa emp = empresaRepo.findById(empresaId)
                    .orElseThrow(() -> new NotFoundException("Empresa no encontrada"));
            p.setEmpresa(emp);
        }
        if (jefeDirectoId != null) {
            JefeDirecto jefe = jefeRepo.findById(jefeDirectoId)
                    .orElseThrow(() -> new NotFoundException("Jefe directo no encontrado"));
            p.setJefeDirecto(jefe);
        }
        if (profesorId != null) {
            Profesor prof = profesorRepo.findById(profesorId)
                    .orElseThrow(() -> new NotFoundException("Profesor supervisor no encontrado"));
            p.setProfesorSupervisor(prof);
        }
        if (fechaInicio != null || fechaTermino != null) {
            validarFechas(fechaInicio != null ? fechaInicio : p.getFechaInicio(),
                          fechaTermino != null ? fechaTermino : p.getFechaTermino());
            if (fechaInicio != null) p.setFechaInicio(fechaInicio);
            if (fechaTermino != null) p.setFechaTermino(fechaTermino);
        }
        if (horasTotales != null) {
            validarHoras(horasTotales);
            p.setHorasTotales(horasTotales);
        }
        if (descripcion != null) p.setDescripcion(descripcion);
        if (estado != null) p.setEstado(estado);

        return practicaRepo.save(p);
    }

    /** Soft delete: marca activo=false (gracias a @SQLDelete) */
    @Transactional
    public void eliminar(Long id) {
        Practica p = obtenerActiva(id);
        practicaRepo.delete(p); // ejecuta UPDATE activo=0
    }

    // ---------- QUERIES ----------
    @Transactional(readOnly = true)
    public Page<Practica> listarPorEstudiante(Long estudianteId, Pageable pageable) {
        return practicaRepo.findByEstudianteIdAndActivoTrue(estudianteId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Practica> listarPorEmpresa(Long empresaId, Pageable pageable) {
        return practicaRepo.findByEmpresaIdAndActivoTrue(empresaId, pageable);
    }

    @Transactional(readOnly = true)
    public Page<Practica> listarPorProfesor(Long profesorId, Pageable pageable) {
        return practicaRepo.findByProfesorSupervisorIdAndActivoTrue(profesorId, pageable);
    }

    @Transactional(readOnly = true)
    public java.util.List<Practica> listarPorEstado(EstadoPractica estado) {
        return practicaRepo.findByEstadoAndActivoTrue(estado);
    }

    @Transactional(readOnly = true)
    public java.util.List<Practica> listarPorRangoFechas(LocalDate desde, LocalDate hasta) {
        return practicaRepo.findByFechaInicioBetweenAndActivoTrue(desde, hasta);
    }
}
