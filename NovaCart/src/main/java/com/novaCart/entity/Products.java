package com.novaCart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(name = "product_name")
    private  String name;

    @Column(name = "category")
    private  String category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "stock")
    private Integer stock;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private  LocalDateTime deletedAt;

}
