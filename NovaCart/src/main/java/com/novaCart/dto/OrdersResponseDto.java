package com.novaCart.dto;

import com.novaCart.utils.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Builder
public class OrdersResponseDto {

    private Long id;
    private Long customerId;
    private String customerName;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private List<OrderItemResponseDto> items;
}