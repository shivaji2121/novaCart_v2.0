package com.novaCart.services.Impl;

import com.novaCart.dto.ProductDto;
import com.novaCart.dto.productResponse.ProductCategoryResponse;
import com.novaCart.entity.ProductsEntity;
import com.novaCart.exception.ResourceAlreadyExistsException;
import com.novaCart.repository.ProductRepository;
import com.novaCart.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public List<ProductDto> getAllProducts() {
       return productRepository.findAll().stream()
                .map(productsEntity ->EntityToDto(productsEntity) )
                .toList();

    }

    @Override
    public List<ProductCategoryResponse> getProductsByCategory() {
        Map<String,List<ProductDto>> productCategoryList=productRepository.findAll().stream().map(this::EntityToDto).collect(Collectors.groupingBy(ProductDto::getCategory,
                Collectors.toList()));

        return productCategoryList.entrySet().stream().map(entry->
                    ProductCategoryResponse.builder()
                            .productStatus(entry.getKey())
                            .products(entry.getValue())
                            .build()
                ).toList();

    };

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
