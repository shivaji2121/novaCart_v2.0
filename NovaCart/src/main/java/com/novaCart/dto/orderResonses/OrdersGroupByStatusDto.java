package com.novaCart.dto.orderResonses;

import com.novaCart.dto.OrdersDto;
import com.novaCart.utils.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrdersGroupByStatusDto {

    private OrderStatus status;
    private List<OrdersDto> orders;
}