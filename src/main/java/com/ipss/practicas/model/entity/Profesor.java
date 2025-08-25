package com.ipss.practicas.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "profesores")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Relación 1:1 con Usuario (rol=PROFESOR) */
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @NotBlank @Size(min = 2, max = 120)
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotBlank @Email
    @Column(nullable = false, length = 150)
    private String email;

    @Size(max = 20)
    private String telefono;

    public Profesor() {}

    public Profesor(Usuario usuario, String nombre, String email, String telefono) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }

    public Long getId() { return id; }
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
