package com.novaCart.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class MonthlyRevenueDTO {
    private Integer month;
    private BigDecimal revenue;
}
