package com.techlab.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.techlab.ecommerce.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaId(Long categoriaId);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    @Query ("SELECT p FROM Producto p WHERE p.stock <= :umbral")
    List<Producto> findProductosConStockBajo(Integer umbral);
}
