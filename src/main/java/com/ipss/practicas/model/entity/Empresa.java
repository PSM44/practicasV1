package com.ipss.practicas.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "empresas", indexes = {
        @Index(name = "ux_empresas_nombre", columnList = "nombre", unique = true)
})
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank @Size(min = 2, max = 150)
    @Column(nullable = false, length = 150, unique = true)
    private String nombre;

    @Size(max = 20)
    private String rut; // opcional

    @Size(max = 200)
    private String direccion;

    @Size(max = 20)
    private String telefono;

    @Email
    @Size(max = 150)
    private String email;

    public Empresa() {}

    public Empresa(String nombre, String rut, String direccion, String telefono, String email) {
        this.nombre = nombre;
        this.rut = rut;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
