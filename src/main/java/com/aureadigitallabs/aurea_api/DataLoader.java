package com.aureadigitallabs.aurea_api;

import com.aureadigitallabs.aurea_api.model.Category;
import com.aureadigitallabs.aurea_api.model.Product;
import com.aureadigitallabs.aurea_api.model.User;
import com.aureadigitallabs.aurea_api.model.UserRole;
import com.aureadigitallabs.aurea_api.repository.CategoryRepository;
import com.aureadigitallabs.aurea_api.repository.ProductRepository;
import com.aureadigitallabs.aurea_api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository; // 1. Nuevo Repositorio

    // 2. Inyectamos UserRepository en el constructor
    public DataLoader(CategoryRepository categoryRepository, 
                      ProductRepository productRepository, 
                      UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
        // --- CARGA DE CATEGORÍAS Y PRODUCTOS ---
        if (categoryRepository.count() == 0) {
            Category skate = categoryRepository.save(new Category(null, "Skate", "skate"));
            Category roller = categoryRepository.save(new Category(null, "Roller", "roller"));
            Category bmx = categoryRepository.save(new Category(null, "BMX", "bmx"));

            System.out.println("✅ Categorías creadas.");

            productRepository.saveAll(Arrays.asList(
                new Product(null, "Tabla Pro Element", 45990.0, "Tabla profesional.", skate, "skatepro"),
                new Product(null, "Skate Completo Tony Hawk", 69990.0, "Ideal principiantes.", skate, "skateboarding"),
                new Product(null, "Mini Skate Cruiser", 25000.0, "Transporte urbano.", skate, "skatemini"),
                new Product(null, "Casco Skate", 19990.0, "Protección certificada.", skate, "cascoskate"),

                new Product(null, "Roller Fitness 500", 89990.0, "Cómodos y rápidos.", roller, "roller"),
                new Product(null, "Patines Agresivos", 120000.0, "Para trucos.", roller, "rollerinline"),
                new Product(null, "Kit Protecciones", 15000.0, "Seguridad total.", roller, "proteccionesroller"),

                new Product(null, "Bicicleta BMX Freestyle", 250000.0, "Cuadro ligero.", bmx, "bmxstunt"),
                new Product(null, "BMX Street Pro", 320000.0, "Resistente.", bmx, "bmxstreet"),
                new Product(null, "Casco Full Face", 45000.0, "Máxima seguridad.", bmx, "cascobmx"),
                new Product(null, "Guantes Ciclismo", 12000.0, "Agarre extra.", bmx, "ciclismo")
            ));
            
            System.out.println("✅ Productos cargados.");
        }

        // --- 3. CARGA DE USUARIOS DE PRUEBA ---
        if (userRepository.count() == 0) {
            // Usuario Administrador
            userRepository.save(new User(
                null, 
                "admin", 
                "admin123", // Contraseña simple para pruebas
                UserRole.ADMIN
            ));

            // Usuario Cliente
            userRepository.save(new User(
                null, 
                "cliente", 
                "cliente123", // Contraseña simple para pruebas
                UserRole.CLIENT
            ));


            System.out.println("✅ Usuarios de prueba creados: 'admin' y 'cliente'");
        }
    }
}