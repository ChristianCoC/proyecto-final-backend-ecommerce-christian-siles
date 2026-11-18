package com.techlab.ecommerce.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techlab.ecommerce.dto.ItemPedidoDTO;
import com.techlab.ecommerce.dto.PedidoRequestDTO;
import com.techlab.ecommerce.exception.ResourceNotFoundException;
import com.techlab.ecommerce.exception.StockInsuficienteException;
import com.techlab.ecommerce.model.DetallePedido;
import com.techlab.ecommerce.model.EstadoPedido;
import com.techlab.ecommerce.model.Pedido;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.model.Usuario;
import com.techlab.ecommerce.repository.PedidoRepository;
import com.techlab.ecommerce.repository.ProductoRepository;
import com.techlab.ecommerce.repository.UsuarioRepository;

@Service 
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;

    public PedidoService(PedidoRepository pedidoRepository, UsuarioRepository usuarioRepository, ProductoRepository productoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
    }

    @Transactional
    public Pedido crearPedido(PedidoRequestDTO request) {

        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + request.getUsuarioId()));

        Pedido nuevoPedido = new Pedido();
        nuevoPedido.setUsuario(usuario);
        nuevoPedido.setEstado(EstadoPedido.PENDIENTE);

        for (ItemPedidoDTO itemDTO : request.getItems()) {
            Producto producto = productoRepository.findById(itemDTO.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + itemDTO.getProductoId()));

            if (producto.getStock() < itemDTO.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre() + ". Stock disponible: " + producto.getStock());
            }

            producto.setStock(producto.getStock() - itemDTO.getCantidad());
            productoRepository.save(producto);

            DetallePedido detalle = new DetallePedido(producto, itemDTO.getCantidad());
            nuevoPedido.agregarItem(detalle);
        }
        return pedidoRepository.save(nuevoPedido);
    }

    public List<Pedido> obtenerPedidosPorUsuario(Long usuarioId) {
        if(!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("Usuario no encontrado con ID: " + usuarioId);
        }
        return pedidoRepository.findByUsuarioIdOrderByFechaDesc(usuarioId);
    }
}
