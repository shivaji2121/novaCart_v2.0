package com.novaCart.services;


import com.novaCart.dto.ProductDto;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {
    ProductDto createProduct( ProductDto productDto);

    List<ProductDto> getAllProducts();
}
