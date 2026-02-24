package com.novaCart.repository;

import com.novaCart.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity,Long> {
    Optional<OrdersEntity> findProductByIdAndDeletedAtIsNull(Long orderId);
}