package com.novaCart.repository;

import com.novaCart.dto.TopOrdersResponse;
import com.novaCart.dto.TopProductDTO;
import com.novaCart.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity,Long> {
    Optional<OrdersEntity> findProductByIdAndDeletedAtIsNull(Long orderId);


    @Query("""
        SELECT new com.novaCart.dto.TopOrdersResponse(
            o.customer.id,
            o.customer.name,
            o.orderStatus,
            SUM(oi.priceAtOrder * oi.quantity)
        ) 
        FROM OrdersEntity o
        JOIN o.orderItems oi
        WHERE o.orderStatus = com.novaCart.utils.OrderStatus.DELIVERED
        GROUP BY o.customer.id, o.customer.name, o.orderStatus
        ORDER BY SUM(oi.priceAtOrder * oi.quantity) DESC
         
    """)
    List<TopOrdersResponse> findTopCustomers();


    @Query("""
        SELECT o.products.id, o.products.name, SUM(o.quantity)
        FROM OrderItemsEntity o
        GROUP BY o.products.id, o.products.name
        ORDER BY SUM(o.quantity) DESC LIMIT 1
        """)
    List<TopProductDTO> findTopSellingProducts();
}