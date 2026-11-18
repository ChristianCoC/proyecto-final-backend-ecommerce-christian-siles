package com.techlab.ecommerce.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity 
@Table(name = "usuarios")
public class Usuario {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank (message = "El nombre del usuario no puede estar vacío")
    @Column (nullable = false)
    private String nombre;

    @NotBlank (message = "El email del usuario no puede estar vacío")
    @Email (message = "El email del usuario no es válido")
    @Column (nullable = false, unique = true)
    private String email;

    @OneToMany (mappedBy = "usuario", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public Usuario() {
    }

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}
