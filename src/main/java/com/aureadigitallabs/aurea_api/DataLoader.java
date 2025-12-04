package com.aureadigitallabs.aurea_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.aureadigitallabs.aurea_api.repository.ProductRepository;
import com.aureadigitallabs.aurea_api.model.Product;
import com.aureadigitallabs.aurea_api.model.UserRole;
import com.aureadigitallabs.aurea_api.repository.UserRepository;
import com.aureadigitallabs.aurea_api.model.User;

import java.util.List;
import com.aureadigitallabs.aurea_api.model.Category;

@Component
public class DataLoader implements CommandLineRunner {

        private final ProductRepository repository;
        private final UserRepository userRepository;

        public DataLoader(ProductRepository repository, UserRepository userRepository) {
                this.repository = repository;
                this.userRepository = userRepository;
        }

        @Override
        public void run(String... args) throws Exception {
                if (repository.count() == 0) {
                        System.out.println("🌱 Base de datos vacía... Insertando datos de prueba...");
                        List<Product> products = List.of(
                                        new Product(null, "Tabla de Skate Completa", 15000.0,
                                                        "Tabla de arce de 7 capas.", Category.SKATE,
                                                        "skatepro"),
                                        new Product(null, "Ruedas de Skate Pro", 25000.0,
                                                        "Juego de 4 ruedas de uretano.", Category.SKATE,
                                                        "rollerinline"),
                                        new Product(null, "Patines Ajustables", 30000.0,
                                                        "Patines cómodos para todas las edades.",
                                                        Category.ROLLER, "roller"),
                                        new Product(null, "Set de Protecciones", 49990.0,
                                                        "Rodilleras, coderas y muñequeras.",
                                                        Category.ROLLER, "proteccionesroller"),
                                        new Product(null, "BMX Freestyle 20", 119000.0,
                                                        "Bicicleta robusta para trucos.", Category.BMX,
                                                        "bmxstunt"),
                                        new Product(null, "Casco BMX", 25990.0, "Certificación de seguridad.",
                                                        Category.BMX, "cascoskate"));
                        repository.saveAll(products);
                        System.out.println("✅ ¡Productos guardados en Supabase correctamente!");
                } else {
                        System.out.println("👌 La base de datos ya tiene datos, no hacemos nada.");
                }
                if (userRepository.count() == 0) { // Necesitas inyectar UserRepository también
                        userRepository.save(new User(null, "admin", "admin123", UserRole.ADMIN));
                        userRepository.save(new User(null, "cliente", "cliente123", UserRole.CLIENT));
                        System.out.println("✅ Usuarios de prueba creados.");
                }
        }
}