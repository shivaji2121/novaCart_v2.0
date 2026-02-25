package com.novaCart.services;

import com.novaCart.advices.ApiResponse;
import com.novaCart.dto.OrdersRequestDto;
import com.novaCart.dto.OrdersResponseDto;
import org.springframework.http.ResponseEntity;

public interface OrdersService {
    OrdersResponseDto createOrder(OrdersRequestDto ordersRequestDto);

    ApiResponse<String>  cancelOrder(Long orderId);
}
