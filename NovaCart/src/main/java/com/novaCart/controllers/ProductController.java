package com.novaCart.controllers;

import com.novaCart.advices.ApiError;
import com.novaCart.advices.ApiResponse;
import com.novaCart.dto.ProductDto;
import com.novaCart.dto.TopProductDTO;
import com.novaCart.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<String>> deleteProductById(@PathVariable Long productId){
        return ResponseEntity.ok(productService.deleteProductById(productId));
    }

    @GetMapping("/list-all")
    public ResponseEntity<Page<ProductDto>> getAllProductsByList(

            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {

        return ResponseEntity.ok(
                productService.getAllProductsByList(search, sortBy, sortDir, minPrice, maxPrice, page, pageSize)
        );
    }


    @GetMapping(path = "/top")
    public ResponseEntity<List<TopProductDTO>> getTopSoldProducts(){
        return ResponseEntity.ok(productService.getTopSoldProducts());
    };


    @GetMapping(path = "/price")
    public ResponseEntity< List<ProductDto> > getTopProductsByPrice(){
        return ResponseEntity.ok(productService.getTopProductsByPrice());
    };
//
//    @GetMapping(path = "/list-all")
//    public ResponseEntity<Page<ProductDto>> getAllProductsByPagination(
//            @RequestParam(defaultValue = "0") Integer page,
//            @RequestParam(defaultValue = "10") Integer pageSize,
//            @RequestParam(required = false) String search,
//            @RequestParam(required = false) String category
//
//    ){
//        return ResponseEntity.ok(productService.getAllProductsByPagination(page,pageSize,search,category));
//    }


}
