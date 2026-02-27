package com.novaCart.services;

import com.novaCart.advices.ApiResponse;
import com.novaCart.dto.OrdersRequestDto;
import com.novaCart.dto.OrdersResponseDto;
import com.novaCart.dto.TopOrdersResponse;
import com.novaCart.dto.orderResonses.OrdersGroupByStatusDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrdersService {
    OrdersResponseDto createOrder(OrdersRequestDto ordersRequestDto);

    ApiResponse<String>  cancelOrder(Long orderId);

    ApiResponse<OrdersResponseDto> getOrderById(Long orderId);

    List<TopOrdersResponse> getTopOrderOfCustomers();

    List<OrdersGroupByStatusDto> ordersGroupBy();
}
