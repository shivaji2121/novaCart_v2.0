package com.novaCart.controllers;

import com.novaCart.dto.OrdersRequestDto;
import com.novaCart.dto.OrdersResponseDto;
import com.novaCart.dto.ProductDto;
import com.novaCart.repository.OrdersRepository;
import com.novaCart.services.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
@Slf4j
public class OrderController {

    private  final OrdersService ordersService;

    @PostMapping(path = "/save")
    public ResponseEntity<OrdersResponseDto> createOrder(@Valid @RequestBody OrdersRequestDto ordersRequestDto){
        OrdersResponseDto productDto=ordersService.createOrder(ordersRequestDto);
        return  new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{orderId}")
    public ResponseEntity<String> c(@PathVariable Long orderId){
        String orderCancelled=ordersService.cancelOrder(orderId);
        return ResponseEntity.ok(orderCancelled);
    }
}
