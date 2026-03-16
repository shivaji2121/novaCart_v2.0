package com.novaCart.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deletedAt;
    private List<OrderItemResponseDto> items;
}