package com.aureadigitallabs.aurea_api.service;

import com.aureadigitallabs.aurea_api.model.Category;
import com.aureadigitallabs.aurea_api.model.Product;
import com.aureadigitallabs.aurea_api.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductService service;

    @Test
    void testGetAllProducts() {
        // Arrange
        // 1. Creamos las categorías ficticias
        Category catSkate = new Category(1L, "Skate", "skate");
        Category catRoller = new Category(2L, "Roller", "roller");

        // 2. Usamos esas categorías en los productos
        Product p1 = new Product(1L, "Skate", 100.0, "Desc", catSkate, "img1");
        Product p2 = new Product(2L, "Roller", 200.0, "Desc", catRoller, "img2");
        
        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // Act
        List<Product> result = service.getAllProducts();

        // Assert
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    void testGetProductById_Success() {
        Category catSkate = new Category(1L, "Skate", "skate");
        Product p = new Product(1L, "Skate", 100.0, "Desc", catSkate, "img1");
        
        when(repository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Product> result = service.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Skate", result.get().getName());
        // Verificamos también que la categoría sea correcta
        assertEquals("Skate", result.get().getCategory().getName());
    }

    @Test
    void testSaveProduct() {
        Category catBmx = new Category(3L, "BMX", "bmx");
        
        Product p = new Product(null, "BMX", 300.0, "New Bike", catBmx, "img3");
        Product savedP = new Product(1L, "BMX", 300.0, "New Bike", catBmx, "img3");
        
        when(repository.save(p)).thenReturn(savedP);

        Product result = service.saveProduct(p);

        assertNotNull(result.getId());
        assertEquals("BMX", result.getName());
    }

    @Test
    void testDeleteProduct() {
        service.deleteProduct(1L);
        verify(repository, times(1)).deleteById(1L);
    }
}