package com.techlab.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity 
@Table (name = "detalle_pedido")
public class DetallePedido {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false)
    private Integer cantidad;

    @Column (nullable = false)
    private Double precioUnitario;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "pedido_id", nullable = false)
    @JsonIgnore 
    private Pedido pedido;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "producto_id", nullable = false)
    private Producto producto;

    public DetallePedido() {
    }

    public DetallePedido(Producto producto, Integer cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return this.precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Pedido getPedido() {
        return this.pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
