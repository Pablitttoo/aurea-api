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

@ExtendWith(MockitoExtension.class) // Habilita el uso de anotaciones @Mock y @InjectMocks
class ProductServiceTest {

    @Mock
    private ProductRepository repository; // Simulamos el repositorio

    @InjectMocks
    private ProductService service; // Inyectamos el mock dentro del servicio real

    @Test
    void testGetAllProducts() {
        // Arrange (Preparar datos)
        Product p1 = new Product(1L, "Skate", 100.0, "Desc", Category.SKATE, "img1");
        Product p2 = new Product(2L, "Roller", 200.0, "Desc", Category.ROLLER, "img2");
        when(repository.findAll()).thenReturn(Arrays.asList(p1, p2));

        // Act (Ejecutar método)
        List<Product> result = service.getAllProducts();

        // Assert (Verificar resultados)
        assertEquals(2, result.size());
        verify(repository, times(1)).findAll(); // Verificamos que se llamó al repo
    }

    @Test
    void testGetProductById_Success() {
        Product p = new Product(1L, "Skate", 100.0, "Desc", Category.SKATE, "img1");
        when(repository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Product> result = service.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Skate", result.get().getName());
    }

    @Test
    void testSaveProduct() {
        Product p = new Product(null, "BMX", 300.0, "New Bike", Category.BMX, "img3");
        Product savedP = new Product(1L, "BMX", 300.0, "New Bike", Category.BMX, "img3");
        
        when(repository.save(p)).thenReturn(savedP);

        Product result = service.saveProduct(p);

        assertNotNull(result.getId());
        assertEquals("BMX", result.getName());
    }
    @Test
    void testDeleteProduct() {
        // Act
        service.deleteProduct(1L);

        // Assert
        // Verificamos que se llamó al método deleteById del repositorio exactamente 1 vez
        verify(repository, times(1)).deleteById(1L);
    }
}