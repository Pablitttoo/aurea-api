package com.aureadigitallabs.aurea_api.controller;

import com.aureadigitallabs.aurea_api.model.Product;
import com.aureadigitallabs.aurea_api.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return service.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestBody Product product) {
        // IMPORTANTE: Forzamos el ID a null para que la Base de Datos genere uno nuevo.
        // Esto evita que intente guardar un producto con ID = 0.
        product.setId(null);

        Product savedProduct = service.saveProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        return service.getProductById(id)
                .map(existingProduct -> {
                    product.setId(id);
                    return ResponseEntity.ok(service.saveProduct(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}