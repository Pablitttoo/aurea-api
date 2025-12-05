package com.aureadigitallabs.aurea_api.controller;

import com.aureadigitallabs.aurea_api.dto.OrderRequest;
import com.aureadigitallabs.aurea_api.model.Order;
import com.aureadigitallabs.aurea_api.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    // Crear un nuevo pedido
    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        return ResponseEntity.ok(service.createOrder(request));
    }

    // Ver historial de pedidos de un usuario
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getOrdersByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(service.getAllOrders());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long id, 
            @RequestParam String status
    ) {
        return ResponseEntity.ok(service.updateOrderStatus(id, status));
    }
}