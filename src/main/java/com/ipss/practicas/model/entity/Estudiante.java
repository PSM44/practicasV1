package com.ipss.practicas.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Relación 1:1 con Usuario (rol=ESTUDIANTE) */
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @NotBlank @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String nombres;

    @NotBlank @Size(min = 2, max = 100)
    @Column(nullable = false, length = 100)
    private String apellidos;

    @NotBlank @Email
    @Column(nullable = false, length = 150)
    private String email;

    @Size(max = 20)
    private String telefono;

    @Size(max = 50)
    private String matricula; // opcional

    public Estudiante() {}

    public Estudiante(Usuario usuario, String nombres, String apellidos, String email, String telefono, String matricula) {
        this.usuario = usuario;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.telefono = telefono;
        this.matricula = matricula;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}
