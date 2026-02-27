package com.novaCart.services;


import com.novaCart.dto.ProductDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDto createProduct( ProductDto productDto);

    List<ProductDto> getAllProducts();

    Page<ProductDto> getAllProductsByPagination(Integer page, Integer pageSize, String search, String category);
}
