package com.novaCart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.novaCart.entity.CustomersEntity;
import com.novaCart.utils.OrderStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrdersDto {
    private  Long id;


    private CustomersEntity customer;

    private OrderStatus orderStatus;

    @NotNull(message = "Order amount is required")
    @Positive(message = "Order amount cannot be negative")
    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private  LocalDateTime deletedAt;
}
