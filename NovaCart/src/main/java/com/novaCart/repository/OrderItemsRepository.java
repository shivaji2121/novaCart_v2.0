package com.novaCart.repository;


import com.novaCart.dto.OrderItemOfLastWeekDto;
import com.novaCart.entity.OrderItemsEntity;
import com.novaCart.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDateTime;
import java.util.List;
import com.novaCart.dto.OrderItemResponseDto;
import org.springframework.data.repository.query.Param;

public interface OrderItemsRepository  extends JpaRepository<OrderItemsEntity, Long> {

//    @Query("SELECT oi FROM OrderItemsEntity oi WHERE oi.orders.createdAt >= :start AND oi.orders.createdAt <= :end")
//    List<OrderItemsEntity> getOrdersLast7Days(LocalDateTime start, LocalDateTime end);

@Query("""
    SELECT new com.novaCart.dto.OrderItemOfLastWeekDto(
        o.id,
        oi.products.id,
        oi.products.name,
        oi.priceAtOrder,
        oi.quantity,
        (oi.priceAtOrder * oi.quantity)
    )
    FROM OrderItemsEntity oi
    JOIN oi.orders o
    WHERE o.createdAt BETWEEN :start AND :end
""")
List<OrderItemOfLastWeekDto> getOrdersLast7Days(@Param("start") LocalDateTime start,
                                                @Param("end") LocalDateTime end);
}
