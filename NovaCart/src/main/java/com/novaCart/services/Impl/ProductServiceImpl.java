package com.novaCart.services.Impl;

import com.novaCart.dto.ProductDto;
import com.novaCart.entity.ProductsEntity;
import com.novaCart.exception.ResourceAlreadyExistsException;
import com.novaCart.repository.ProductRepository;
import com.novaCart.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        boolean exist=productRepository.existsByNameAndDeletedAtIsNull(productDto.getName());
        if(exist){
            throw new ResourceAlreadyExistsException("product already exist");
        }
        ProductsEntity productsEntity=toEntity(productDto);
        ProductsEntity savedProduct= productRepository.save(productsEntity);
        return EntityToDto(savedProduct);
    }

    private ProductsEntity toEntity(ProductDto productDto){
        return  ProductsEntity.builder()
                .name(productDto.getName())
                .category(productDto.getCategory())
                .price(productDto.getPrice())
                .quantity(productDto.getQuantity())
                .description(productDto.getDescription())
                .build();
    }

    private ProductDto EntityToDto(ProductsEntity productsEntity){
        return ProductDto.builder()
                .id(productsEntity.getId())
                .name(productsEntity.getName())
                .category(productsEntity.getCategory())
                .price(productsEntity.getPrice())
                .description(productsEntity.getDescription())
                .quantity(productsEntity.getQuantity())
                .createdAt(productsEntity.getCreatedAt())
                .updatedAt(productsEntity.getUpdatedAt())
                .deletedAt(productsEntity.getDeletedAt())
                .build();
    }
}
