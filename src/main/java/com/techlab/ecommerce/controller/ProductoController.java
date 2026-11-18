package com.techlab.ecommerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.service.ProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/productos")
@Tag(name = "Productos", description = "API para la gestión del catalogo de productos") 
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos disponibles en el catalogo")
    @GetMapping
    public ResponseEntity<List<Producto>> listarTodos() {
        return ResponseEntity.ok(productoService.listarTodos());
    }

    @Operation(summary = "Obtener producto por ID", description = "Obtiene un producto específico por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.obtenerPorId(id));
    }

    @Operation (summary = "Crear un nuevo producto", description = "Crea y guarda un nuevo producto en el catalogo")
    @PostMapping
    public ResponseEntity<Producto> crear(@Valid @RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.guardar(producto), HttpStatus.CREATED);
    }

    @Operation(summary = "Actualizar un producto existente", description = "Actualiza los detalles de un producto existente por su ID")
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @Valid @RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.actualizar(id, producto));
    }

    @Operation(summary = "Eliminar un producto", description = "Elimina un producto del catalogo por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
