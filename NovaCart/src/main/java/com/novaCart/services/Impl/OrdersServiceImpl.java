package com.novaCart.services.Impl;

import com.novaCart.advices.ApiResponse;
import com.novaCart.dto.OrderItemResponseDto;
import com.novaCart.dto.OrdersRequestDto;
import com.novaCart.dto.OrdersResponseDto;
import com.novaCart.dto.TopOrdersResponse;
import com.novaCart.entity.CustomersEntity;
import com.novaCart.entity.OrderItemsEntity;
import com.novaCart.entity.OrdersEntity;
import com.novaCart.entity.ProductsEntity;
import com.novaCart.exception.InvalidQuantityException;
import com.novaCart.exception.OrderCannotBeCancelledException;
import com.novaCart.exception.ResourceNofFoundException;
import com.novaCart.repository.CustomerRepository;
import com.novaCart.repository.OrderItemsRepository;
import com.novaCart.repository.OrdersRepository;
import com.novaCart.repository.ProductRepository;
import com.novaCart.services.OrdersService;
import com.novaCart.utils.OrderStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdersServiceImpl implements OrdersService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;


    @Override
    @Transactional
    public OrdersResponseDto createOrder(OrdersRequestDto ordersRequestDto) {
        CustomersEntity customer = customerRepository.findActiveCustomerById(ordersRequestDto.getCustomerId())
                .orElseThrow(() -> new ResourceNofFoundException("Customer not found"));
log.info("1..>{}"+customer);
        List<OrderItemsEntity> orderItems = ordersRequestDto.getItems().stream().map(itemDto -> {
            ProductsEntity product = productRepository.findByIdAndDeletedAtIsNull(itemDto.getProductId())
                    .orElseThrow(() -> new ResourceNofFoundException("product not found with id: " + itemDto.getProductId()));

            if (itemDto.getQuantity() <= 0) {
                throw new InvalidQuantityException("Quantity must be greater than zero: " + itemDto.getProductId());
            }

            if (itemDto.getQuantity() > product.getQuantity()) {
                throw new InvalidQuantityException("Sorry Only " + product.getQuantity() + " unit(s) of '" + product.getName() + "' are available."
                );
            }

            product.setQuantity(product.getQuantity() - itemDto.getQuantity());
            productRepository.save(product);

            return OrderItemsEntity.builder()
                    .products(product)
                    .quantity(itemDto.getQuantity())
                    .priceAtOrder(product.getPrice().multiply(BigDecimal.valueOf(itemDto.getQuantity())))
                    .build();

        }).toList();
        log.info("1..>{}"+orderItems);
        BigDecimal totalPrice = orderItems.stream()
                .map(OrderItemsEntity::getPriceAtOrder)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        OrdersEntity order = OrdersEntity.builder()
                .customer(customer)
                .orderStatus(OrderStatus.PENDING)
                .totalAmount(totalPrice)
                .orderItems(orderItems)
                .build();

//        orderItems.forEach(item -> item.setOrders(order));

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

        log.info("2..>{}"+itemsDto);
        return OrdersResponseDto.builder()
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

    @Override
    @Transactional
    public ApiResponse<String> cancelOrder(Long orderId) {
        OrdersEntity savedOrder = ordersRepository.findProductByIdAndDeletedAtIsNull(orderId).
                orElseThrow(() -> new ResourceNofFoundException("Order not found with id: " + orderId));

        if (savedOrder.getOrderStatus() == OrderStatus.CANCELLED) {
            throw new OrderCannotBeCancelledException("Order is already cancelled");
        }

        if (savedOrder.getOrderStatus() == OrderStatus.DELIVERED) {
            throw new OrderCannotBeCancelledException("Ordered cannot be cancelled");
        }


        for (OrderItemsEntity items : savedOrder.getOrderItems()) {
            ProductsEntity product = items.getProducts();
            product.setQuantity(product.getQuantity() + items.getQuantity());

            product.setUpdatedAt(LocalDateTime.now());
            productRepository.save(product);
        }


        savedOrder.setOrderStatus(OrderStatus.CANCELLED);
        savedOrder.setUpdatedAt(LocalDateTime.now());

        ordersRepository.save(savedOrder);

        return new ApiResponse<>("Order cancelled successfully");
    }


    public ApiResponse<OrdersResponseDto> getOrderById(Long orderId) {
        OrdersEntity orders = ordersRepository.findProductByIdAndDeletedAtIsNull(orderId)
                .orElseThrow(() -> new ResourceNofFoundException("Order not found with id: " + orderId));


        BigDecimal totalPrice = orders.getOrderItems().stream()
                .map(OrderItemsEntity::getPriceAtOrder)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<OrderItemResponseDto> ordersItemsList = orders.getOrderItems().stream()
                .map(orderItemsEntity -> OrderItemResponseDto.builder()
                        .productName(orderItemsEntity.getProducts().getName())
                        .quantity(orderItemsEntity.getQuantity())
                        .productId(orderItemsEntity.getProducts().getId())
                        .price(orderItemsEntity.getProducts().getPrice())
                        .totalPrice(orderItemsEntity.getPriceAtOrder())
                        .build())
                .toList();


        OrdersResponseDto ordersResponseDto = OrdersResponseDto.builder()
                .id(orders.getId())
                .customerId(orders.getCustomer().getId())
                .customerName(orders.getCustomer().getName())
                .orderStatus(orders.getOrderStatus())
                .totalAmount(totalPrice)
                .createdAt(orders.getCreatedAt())
                .updatedAt(orders.getUpdatedAt())
                .deletedAt(orders.getDeletedAt())
                .items(ordersItemsList)
                .build();
        return new ApiResponse<>(ordersResponseDto);
    }

    @Override
    public List<TopOrdersResponse> getTopOrderOfCustomers(Integer limit) {
        log.info("{}"+limit);
        List<TopOrdersResponse> topCustomers = ordersRepository.findTopCustomers().stream().limit(limit).toList();
        log.info("{}"+topCustomers);
        return topCustomers;
    }

    ;


}






