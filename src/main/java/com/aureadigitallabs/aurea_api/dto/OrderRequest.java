package com.aureadigitallabs.aurea_api.dto;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private Long userId; // Quién compra
    private List<OrderItemRequest> items; // Qué compra
}