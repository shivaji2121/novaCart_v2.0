package com.novaCart.controllers;

import com.novaCart.dto.ProductDto;
import com.novaCart.dto.productResponse.ProductCategoryResponse;
import com.novaCart.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("products")
public class ProductController {

    private  final ProductService productService;

    @PostMapping(path = "/save")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto){
        ProductDto product=productService.createProduct(productDto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping(path = "/list")
    public  ResponseEntity<List<ProductDto>> getAllProducts(){
        List<ProductDto> list=productService.getAllProducts();
        return ResponseEntity.ok(list);
    }

    @GetMapping(path = "/categories")
    public ResponseEntity<List<ProductCategoryResponse>> getProductsByCategory(){
        return ResponseEntity.ok(productService.getProductsByCategory());
    }
}
