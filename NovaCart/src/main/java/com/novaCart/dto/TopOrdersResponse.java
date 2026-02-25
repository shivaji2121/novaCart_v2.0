package com.novaCart.dto;

import com.novaCart.utils.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TopOrdersResponse {
    private Long customerId;
    private String customerName;
    private OrderStatus orderStatus;
    private BigDecimal totalPurchasedAmount;

}
