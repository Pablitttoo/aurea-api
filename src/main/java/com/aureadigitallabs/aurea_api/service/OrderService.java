package com.aureadigitallabs.aurea_api.service;

import com.aureadigitallabs.aurea_api.dto.OrderItemRequest;
import com.aureadigitallabs.aurea_api.dto.OrderRequest;
import com.aureadigitallabs.aurea_api.model.*;
import com.aureadigitallabs.aurea_api.repository.OrderRepository;
import com.aureadigitallabs.aurea_api.repository.ProductRepository;
import com.aureadigitallabs.aurea_api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional // Si algo falla, no se guarda nada (integridad de datos)
    public Order createOrder(OrderRequest request) {
        // 1. Buscar Usuario
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // 2. Crear la Orden vacía
        Order order = new Order();
        order.setUser(user);
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING); // Asumimos pagado/completado por ahora

        // 3. Procesar Items y Calcular Total
        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado: " + itemRequest.getProductId()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());
            orderItem.setOrder(order); // Vincular item a la orden

            items.add(orderItem);
            total += product.getPrice() * itemRequest.getQuantity();
        }

        order.setItems(items);
        order.setTotal(total);

        // 4. Guardar todo
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return orderRepository.findByUserOrderByDateDesc(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll(); 
    }

    public Order updateOrderStatus(Long orderId, String newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        
        try {
            OrderStatus status = OrderStatus.valueOf(newStatus.toUpperCase());
            order.setStatus(status);
            return orderRepository.save(order);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Estado inválido: " + newStatus);
        }
    }
}