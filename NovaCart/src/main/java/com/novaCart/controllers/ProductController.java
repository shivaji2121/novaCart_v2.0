package com.novaCart.controllers;

import com.novaCart.dto.ProductDto;
import com.novaCart.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
