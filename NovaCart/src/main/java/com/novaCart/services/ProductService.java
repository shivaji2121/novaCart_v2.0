package com.novaCart.services;


import com.novaCart.dto.ProductDto;
import com.novaCart.dto.productResponse.ProductCategoryResponse;

import java.util.List;

public interface ProductService {
    ProductDto createProduct( ProductDto productDto);

    List<ProductDto> getAllProducts();

    List<ProductCategoryResponse> getProductsByCategory();
}
