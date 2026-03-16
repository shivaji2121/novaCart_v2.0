package com.novaCart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TopProductDTO {

    private Long productId;
    private String productName;
    private Long totalSold;

}