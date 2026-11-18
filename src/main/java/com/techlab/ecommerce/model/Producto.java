package com.techlab.ecommerce.model;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity 
@Table (name = "productos")
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del producto no puede estar vacío")
    @Column(nullable = false)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    @NotNull (message = "El precio del producto no puede estar vacío")
    @Positive(message = "El precio del producto debe ser un valor mayor que cero")
    @Column(nullable = false)
    private Double precio;

    @URL (message = "La URL de la imagen no es válida")
    private String imagenUrl;

    @NotNull (message = "El stock del producto no puede estar vacío")
    @Min (value = 0, message = "El stock del producto no puede ser negativo")
    @Column (nullable = false)
    private Integer stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    // Constructor vacío requerido por JPA
    public Producto() {
    }

    public Producto(String nombre, String descripcion, Double precio, String imagenUrl, Integer stock, Categoria categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenUrl = imagenUrl;
        this.stock = stock;
        this.categoria = categoria;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
