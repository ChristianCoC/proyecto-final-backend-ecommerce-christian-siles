package com.techlab.ecommerce.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity 
@Table(name = "pedidos")
public class Pedido {

    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp 
    @Column (nullable = false, updatable = false)
    private LocalDateTime fecha;

    @Enumerated (EnumType.STRING)
    @Column (nullable = false)
    private EstadoPedido estado;

    @Column (nullable = false)
    private Double total;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany (mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> items = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public Pedido() {}

    public void agregarItem(DetallePedido item) {
        items.add(item);
        item.setPedido(this);
        recalcularTotal();
    }

    private void recalcularTotal() {
        total = items.stream()
                .mapToDouble(item -> item.getCantidad() * item.getPrecioUnitario())
                .sum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public Double getTotal() {
        return total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<DetallePedido> getItems() {
        return items;
    }

    public void setItems(List<DetallePedido> items) {
        this.items = items;
        recalcularTotal();
    }
}
