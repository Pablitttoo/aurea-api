package com.aureadigitallabs.aurea_api.controller;

import com.aureadigitallabs.aurea_api.model.Category;
import com.aureadigitallabs.aurea_api.repository.CategoryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryRepository repository;

    public CategoryController(CategoryRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    // --- NUEVOS MÉTODOS PARA EL ADMIN DE ANDROID ---

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return repository.save(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category categoryDetails) {
        return repository.findById(id)
                .map(category -> {
                    category.setName(categoryDetails.getName());
                    category.setIconName(categoryDetails.getIconName());
                    return ResponseEntity.ok(repository.save(category));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}