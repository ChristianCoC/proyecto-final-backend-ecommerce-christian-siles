package com.techlab.ecommerce.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.techlab.ecommerce.model.Categoria;
import com.techlab.ecommerce.model.Producto;
import com.techlab.ecommerce.model.Usuario;
import com.techlab.ecommerce.repository.CategoriaRepository;
import com.techlab.ecommerce.repository.ProductoRepository;
import com.techlab.ecommerce.repository.UsuarioRepository;

@Component 
public class DataInitializer implements CommandLineRunner {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final UsuarioRepository usuarioRepository;

    public DataInitializer(CategoriaRepository categoriaRepository, ProductoRepository productoRepository, UsuarioRepository usuarioRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(categoriaRepository.count() == 0 && usuarioRepository.count() == 0) {

            System.out.println("--- Inicializando datos de prueba para la demostración ---");

            Usuario usuario = new Usuario("Christian Siles", "christian.siles@example.com");
            usuarioRepository.save(usuario);

            Categoria catCafe = new Categoria("Cafetería Gourmet", "Productos de café de alta calidad y accesorios relacionados.");
            Categoria catTe = new Categoria("Tés e infusiones", "Variedad de tés, tisanas y accesorios para la preparación de infusiones.");
            categoriaRepository.saveAll(List.of(catCafe, catTe));

            Producto p1 = new Producto("Café Colombia Supremo", "Notas a caramelo y frutos rojos.", 
                    5200.0, "https://ejemplo.com/cafe-colombia.jpg", 40, catCafe);
            
            Producto p2 = new Producto("Té Verde Matcha", "Polvo fino de té verde japonés.", 
                    7800.0, "https://ejemplo.com/matcha.jpg", 15, catTe);
            
            Producto p3 = new Producto("Café Etiopía Yirgacheffe", "Aroma floral y cuerpo sedoso.", 
                    6400.0, "https://ejemplo.com/cafe-etiopia.jpg", 25, catCafe);

            productoRepository.saveAll(List.of(p1, p2, p3));

            System.out.println("--- Datos inicializados correctamente ---");
        }
    }
}
