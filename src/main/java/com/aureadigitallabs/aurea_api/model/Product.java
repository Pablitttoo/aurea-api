package com.aureadigitallabs.aurea_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data // Genera Getters, Setters, ToString, etc.
@NoArgsConstructor // Constructor vacío (Obligatorio para JPA)
@AllArgsConstructor // Constructor con todos los campos
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;

    @Column(length = 1000) // Permitimos descripciones largas
    private String description;

    @Enumerated(EnumType.STRING) // Guarda "SKATE" como texto en la BD
    private Category category;

    // Guardamos el nombre del archivo de imagen (ej: "skatepro")
    private String imageName;
}