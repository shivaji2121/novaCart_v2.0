package com.novaCart.services;


import com.novaCart.dto.ProductDto;
import jakarta.validation.Valid;

public interface ProductService {
    ProductDto createProduct( ProductDto productDto);
}
