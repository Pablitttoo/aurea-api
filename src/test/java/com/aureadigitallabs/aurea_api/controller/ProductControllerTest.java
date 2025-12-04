package com.aureadigitallabs.aurea_api.controller;

import com.aureadigitallabs.aurea_api.model.Category;
import com.aureadigitallabs.aurea_api.model.Product;
import com.aureadigitallabs.aurea_api.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllProducts() throws Exception {
        Product p1 = new Product(1L, "Skate", 15000.0, "Desc", Category.SKATE, "img");
        when(service.getAllProducts()).thenReturn(Arrays.asList(p1));

        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Skate"));
    }

    @Test
    void shouldCreateProduct() throws Exception {
        Product newProduct = new Product(null, "Casco", 5000.0, "Seguridad", Category.BMX, "img");
        Product savedProduct = new Product(1L, "Casco", 5000.0, "Seguridad", Category.BMX, "img");

        when(service.saveProduct(any(Product.class))).thenReturn(savedProduct);

        mockMvc.perform(post("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    void shouldReturnProductById() throws Exception {
        Product p = new Product(1L, "Roller", 30000.0, "Pro", Category.ROLLER, "img");
        when(service.getProductById(1L)).thenReturn(Optional.of(p));

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Roller"));
    }

    @Test
    void shouldDeleteProduct() throws Exception {
        doNothing().when(service).deleteProduct(1L);

        mockMvc.perform(delete("/api/products/1"))
                .andExpect(status().isOk());
    }
}