package com.novaCart.services;

import com.novaCart.advices.ApiResponse;
import com.novaCart.dto.*;
import com.novaCart.entity.OrderItemsEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrdersService {
    OrdersResponseDto createOrder(OrdersRequestDto ordersRequestDto);

    ApiResponse<String>  cancelOrder(Long orderId);

    ApiResponse<OrdersResponseDto> getOrderById(Long orderId);

    List<TopOrdersResponse> getTopOrderOfCustomers(Integer limit);

    List<OrderItemOfLastWeekDto> getLastWeekOrders();
}
