package com.techlab.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.techlab.ecommerce.exception.ResourceNotFoundException;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.repository.ProductoRepository;

@Service 
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarTodos() {
        return productoRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    public Producto guardar(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizar(Long id, Producto detalles) {
        Producto productoExistente = obtenerPorId(id);
        productoExistente.setNombre(detalles.getNombre());
        productoExistente.setDescripcion(detalles.getDescripcion());
        productoExistente.setPrecio(detalles.getPrecio());
        productoExistente.setStock(detalles.getStock());
        productoExistente.setCategoria(detalles.getCategoria());
        return productoRepository.save(productoExistente);
    }

    public void eliminar(Long id) {
        Producto productoExistente = obtenerPorId(id);
        productoRepository.delete(productoExistente);
    }
}
