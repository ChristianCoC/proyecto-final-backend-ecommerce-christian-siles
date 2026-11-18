package com.techlab.ecommerce.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.techlab.ecommerce.dto.PedidoRequestDTO;
import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/pedidos")
@Tag(name = "Pedidos", description = "API para el procesamiento de compras e historial de pedidos") 
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Crear un nuevo pedido (Checkout)", description = "Crea un nuevo pedido para un usuario específico con los productos seleccionados")
    @PostMapping
    public ResponseEntity<Pedido> crearPedido(@Valid @RequestBody PedidoRequestDTO request) {
        Pedido nuevoPedido = pedidoService.crearPedido(request);
        return new ResponseEntity<>(nuevoPedido, HttpStatus.CREATED);
    }

    @Operation (summary = "Obtener pedidos por usuario", description = "Obtiene todos los pedidos realizados por un usuario específico")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(pedidoService.obtenerPedidosPorUsuario(usuarioId));
    }
}
