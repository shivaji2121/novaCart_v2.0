package com.novaCart.services;


import com.novaCart.advices.ApiResponse;
import com.novaCart.dto.ProductDto;
import com.novaCart.dto.TopProductDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductDto createProduct( ProductDto productDto);

    List<ProductDto> getAllProducts();

    Page<ProductDto> getAllProductsByPagination(Integer page, Integer pageSize, String search, String category);

    ApiResponse<String> deleteProductById(Long productId);

    Page<ProductDto> getAllProductsByList(String search, String sortBy, String sortDir, Double minPrice, Double maxPrice, Integer page, Integer pageSize);

    List<TopProductDTO> getTopSoldProducts();

    List<ProductDto>  getTopProductsByPrice();
}
