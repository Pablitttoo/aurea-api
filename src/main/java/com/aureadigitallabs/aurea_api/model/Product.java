package com.aureadigitallabs.aurea_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @Column(length = 1000)
    private String description;

    @ManyToOne 
    @JoinColumn(name = "category_id") // Crea una columna category_id en la tabla products
    private Category category;

    private String imageName;
}