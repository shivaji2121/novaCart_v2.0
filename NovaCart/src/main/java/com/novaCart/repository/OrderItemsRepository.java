package com.novaCart.repository;


import com.novaCart.entity.OrderItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemsRepository  extends JpaRepository<OrderItemsEntity, Long> {
}
