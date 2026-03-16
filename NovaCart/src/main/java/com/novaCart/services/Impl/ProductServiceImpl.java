package com.novaCart.services.Impl;

import com.novaCart.advices.ApiResponse;
import com.novaCart.dto.ProductDto;
import com.novaCart.dto.TopProductDTO;
import com.novaCart.entity.OrderItemsEntity;
import com.novaCart.entity.ProductsEntity;
import com.novaCart.exception.ResourceAlreadyExistsException;
import com.novaCart.repository.OrderItemsRepository;
import com.novaCart.repository.OrdersRepository;
import com.novaCart.repository.ProductRepository;
import com.novaCart.services.ProductService;
import com.novaCart.utils.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final OrdersRepository ordersRepository;


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
    public Page<ProductDto> getAllProductsByPagination(Integer page, Integer pageSize, String search, String category) {
        int pageNumber = (page <= 0) ? 0 : page - 1;
        Pageable pageable= PageRequest.of(pageNumber,pageSize,  Sort.by(Sort.Direction.DESC, "createdAt"));

        search=(search==null)?"":search;

        category=(category==null)?"":category;

        Page<ProductsEntity> listOfProducts=productRepository.findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndDeletedAtIsNull(search,category,pageable);
        return listOfProducts.map(this::EntityToDto);
    }

    @Override
    public ApiResponse<String> deleteProductById(Long productId) {
        ProductsEntity savedProduct=productRepository.findByIdAndDeletedAtIsNull(productId).orElseThrow(()->new RuntimeException("Product not found with id: "+productId));
        savedProduct.setDeletedAt(LocalDateTime.now());
        productRepository.save(savedProduct);
        return new ApiResponse<>("Product deleted successfully");
    }

    @Override
    public Page<ProductDto> getAllProductsByList(String search, String sortBy, String sortDir, Double minPrice, Double maxPrice, Integer page, Integer pageSize) {

        int pageNumber=(page <= 0) ? 0 : page - 1;

        search = (search == null) ? "" : search;

        sortBy = (sortBy == null || sortBy.isBlank()) ? "createdAt" : sortBy;
        sortDir = (sortDir == null || sortDir.isBlank()) ? "desc" : sortDir;

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<ProductsEntity> listOfProducts = productRepository.findProducts(search, minPrice, maxPrice, pageable);
        return listOfProducts.map(this::EntityToDto);
    }

    @Override
    public List<TopProductDTO> getTopSoldProducts() {
//        return ordersRepository.findTopSellingProducts().stream().map(item->
//                TopProductDTO.builder()
//                        .productId(item.getProductId())
//                        .productName(item.getProductName())
//                        .totalSold(item.getTotalSold())
//                        .build())
//                .limit(1)
//                .toList();

        return ordersRepository.findTopSellingProducts();
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
