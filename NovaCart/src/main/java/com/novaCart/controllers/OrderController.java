package com.novaCart.controllers;

import com.novaCart.advices.ApiResponse;
import com.novaCart.dto.*;
import com.novaCart.dto.orderResonses.OrdersGroupByStatusDto;
import com.novaCart.repository.OrdersRepository;
import com.novaCart.services.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.cancelOrder(orderId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrdersResponseDto>> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(ordersService.getOrderById(orderId));
    }

    @GetMapping("/analytics/top-customers")
    public ResponseEntity<List<TopOrdersResponse>> getTopOrderOfCustomers() {
        return ResponseEntity.ok(ordersService.getTopOrderOfCustomers());
    }

    @GetMapping(path = "/categories")
    public ResponseEntity<List<OrdersGroupByStatusDto>> ordersGroupBy() {
        return ResponseEntity.ok(ordersService.ordersGroupBy());
    }


//    @GetMapping(path = "/api/analytics/monthly-revenue")
//    public ResponseEntity<MonthlyRevenueDTO>  get

}
