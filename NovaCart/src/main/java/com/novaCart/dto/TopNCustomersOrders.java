package com.novaCart.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopNCustomersOrders {
    private Long customerId;
    private String name;
    private Integer ordersCount;
}
