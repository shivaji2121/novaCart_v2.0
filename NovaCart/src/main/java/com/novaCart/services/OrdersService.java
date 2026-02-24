package com.novaCart.services;

import com.novaCart.dto.OrdersRequestDto;
import com.novaCart.dto.OrdersResponseDto;
import com.novaCart.dto.ProductDto;
import jakarta.validation.Valid;

public interface OrdersService {
    OrdersResponseDto createOrder(OrdersRequestDto ordersRequestDto);

    String cancelOrder(Long orderId);
}
