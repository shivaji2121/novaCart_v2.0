package com.novaCart.services.Impl;

import com.novaCart.dto.OrderItemResponseDto;
import com.novaCart.dto.OrdersRequestDto;
import com.novaCart.dto.OrdersResponseDto;
import com.novaCart.entity.CustomersEntity;
import com.novaCart.entity.OrderItemsEntity;
import com.novaCart.entity.OrdersEntity;
import com.novaCart.entity.ProductsEntity;
import com.novaCart.exception.InvalidQuantityException;
import com.novaCart.exception.ResourceNofFoundException;
import com.novaCart.repository.CustomerRepository;
import com.novaCart.repository.OrderItemsRepository;
import com.novaCart.repository.OrdersRepository;
import com.novaCart.repository.ProductRepository;
import com.novaCart.services.OrdersService;
import com.novaCart.utils.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService{

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;



    @Override
    @Transactional
    public OrdersResponseDto createOrder(OrdersRequestDto ordersRequestDto) {
     CustomersEntity customer= customerRepository.findActiveCustomerById(ordersRequestDto.getCustomerId())
                .orElseThrow(()-> new ResourceNofFoundException("Customer not found"));

        List<OrderItemsEntity> orderItems=ordersRequestDto.getItems().stream().map(itemDto->{
          ProductsEntity   product=productRepository.findByIdAndDeletedAtIsNull(itemDto.getProductId())
                  .orElseThrow(()->new ResourceNofFoundException("product not found with id: "+itemDto.getProductId()));

          if(itemDto.getQuantity()<=0){
              throw new InvalidQuantityException("Quantity must be greater than zero: " + itemDto.getProductId());
          }

          if(itemDto.getQuantity()>product.getQuantity()){
              throw  new InvalidQuantityException("Quantity should not exceed the stock");
          }

          product.setQuantity(product.getQuantity()- itemDto.getQuantity());
          productRepository.save(product);

            return OrderItemsEntity.builder()
                    .products(product)
                    .quantity(itemDto.getQuantity())
                    .priceAtOrder(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())))
                    .build();

        }).toList();

        BigDecimal totalPrice = orderItems.stream()
                .map(OrderItemsEntity::getPriceAtOrder)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrdersEntity order = OrdersEntity.builder()
                .customer(customer)
                .orderStatus(OrderStatus.PENDING)
                .totalAmount(totalPrice)
                .orderItems(orderItems)
                .build();

        orderItems.forEach(item -> item.setOrders(order));

        OrdersEntity savedOrder = ordersRepository.save(order);

        List<OrderItemResponseDto> itemsDto = savedOrder.getOrderItems().stream()
                .map(oi -> OrderItemResponseDto.builder()
                        .productId(oi.getProducts().getId())
                        .productName(oi.getProducts().getName())
                        .price(oi.getProducts().getPrice())
                        .quantity(oi.getQuantity())
                        .totalPrice(oi.getPriceAtOrder())
                        .build())
                .toList();

        return  OrdersResponseDto.builder()
                .id(savedOrder.getId())
                .customerId(customer.getId())
                .customerName(customer.getName())
                .orderStatus(savedOrder.getOrderStatus())
                .totalAmount(savedOrder.getTotalAmount())
                .createdAt(savedOrder.getCreatedAt())
                .updatedAt(savedOrder.getUpdatedAt())
                .deletedAt(savedOrder.getDeletedAt())
                .items(itemsDto)
                .build();
    }
    }



