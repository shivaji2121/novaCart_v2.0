package com.novaCart.dto.productResponse;

import com.novaCart.dto.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductCategoryResponse {

    private String productStatus;
    private List<ProductDto> products;
}
