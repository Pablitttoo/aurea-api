package com.aureadigitallabs.aurea_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private Integer quantity;
    
    // Guardamos el precio histórico (el precio del producto podría cambiar mañana)
    private Double priceAtPurchase; 

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore // Evita bucles infinitos al serializar a JSON
    private Order order;
}