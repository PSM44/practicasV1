package com.ipss.practicas.model.entity;

import com.ipss.practicas.model.enums.EstadoPractica;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "practicas")
public class Practica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Alumno que realiza la práctica */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    /** Empresa donde se realiza */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    /** Jefe directo en la empresa */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "jefe_directo_id", nullable = false)
    private JefeDirecto jefeDirecto;

    /** Profesor supervisor interno */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id", nullable = false)
    private Profesor profesorSupervisor;

    @NotNull
    private LocalDate fechaInicio;

    @NotNull
    private LocalDate fechaTermino;

    @Min(1) @Max(2000)
    private Integer horasTotales;

    @Size(max = 1000)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPractica estado = EstadoPractica.REGISTRADA;

    public Practica() {}

    public Practica(Estudiante estudiante, Empresa empresa, JefeDirecto jefeDirecto,
                    Profesor profesorSupervisor, LocalDate fechaInicio, LocalDate fechaTermino,
                    Integer horasTotales, String descripcion, EstadoPractica estado) {
        this.estudiante = estudiante;
        this.empresa = empresa;
        this.jefeDirecto = jefeDirecto;
        this.profesorSupervisor = profesorSupervisor;
        this.fechaInicio = fechaInicio;
        this.fechaTermino = fechaTermino;
        this.horasTotales = horasTotales;
        this.descripcion = descripcion;
        if (estado != null) this.estado = estado;
    }

    public Long getId() { return id; }
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public Empresa getEmpresa() { return empresa; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public JefeDirecto getJefeDirecto() { return jefeDirecto; }
    public void setJefeDirecto(JefeDirecto jefeDirecto) { this.jefeDirecto = jefeDirecto; }
    public Profesor getProfesorSupervisor() { return profesorSupervisor; }
    public void setProfesorSupervisor(Profesor profesorSupervisor) { this.profesorSupervisor = profesorSupervisor; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaTermino() { return fechaTermino; }
    public void setFechaTermino(LocalDate fechaTermino) { this.fechaTermino = fechaTermino; }
    public Integer getHorasTotales() { return horasTotales; }
    public void setHorasTotales(Integer horasTotales) { this.horasTotales = horasTotales; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public EstadoPractica getEstado() { return estado; }
    public void setEstado(EstadoPractica estado) { this.estado = estado; }
}
