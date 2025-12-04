package com.aureadigitallabs.aurea_api.repository;

import com.aureadigitallabs.aurea_api.model.Category;
import com.aureadigitallabs.aurea_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Método personalizado para buscar por categoría
    // Spring crea el SQL automáticamente al leer el nombre del método
    List<Product> findByCategory(Category category);
}